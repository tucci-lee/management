package cn.tucci.management.controller.sys;

import cn.tucci.management.core.annotation.Limit;
import cn.tucci.management.core.constant.CacheConstant;
import cn.tucci.management.core.constant.TimeConstant;
import cn.tucci.management.core.exception.BusinessException;
import cn.tucci.management.core.response.Result;
import cn.tucci.management.core.response.ResultStatus;
import cn.tucci.management.core.util.Assert;
import cn.tucci.management.core.util.BCrypt;
import cn.tucci.management.core.util.WebUtil;
import cn.tucci.management.model.body.ChangePwdBody;
import cn.tucci.management.model.body.LoginBody;
import cn.tucci.management.model.domain.sys.SysLoginLog;
import cn.tucci.management.model.domain.sys.SysUser;
import cn.tucci.management.service.sys.SysLoginLogService;
import cn.tucci.management.service.sys.SysUserService;
import cn.tucci.management.shiro.util.ShiroUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author tucci.lee
 */
@RestController
public class AuthController {

    private final SysLoginLogService sysLoginLogService;
    private final SysUserService sysUserService;
    private final SimpleAsyncTaskExecutor simpleAsyncTaskExecutor;

    @Autowired
    public AuthController(SysLoginLogService sysLoginLogService, SysUserService sysUserService, SimpleAsyncTaskExecutor simpleAsyncTaskExecutor) {
        this.sysLoginLogService = sysLoginLogService;
        this.sysUserService = sysUserService;
        this.simpleAsyncTaskExecutor = simpleAsyncTaskExecutor;
    }

    /**
     * 登陆接口：
     * 1：获取session中验证码信息（验证码，验证码的获取时间）
     * 2：删除session中验证码信息
     * 3：判断验证码信息是否有效，有值、未过期、与用户填写的验证码相同
     * 4：执行shiro认证
     * 5：捕获过程中的所有异常，抛出对应的错误
     * 6：finally中记录登陆日志
     *
     * @param body    body
     * @param session session
     * @return Result
     */
    @Limit(value = "#body.account", frequency = 3, cycle = 5 * 60, expireTime = 5 * 60, message = "登陆频繁，5分钟后重试")
    @PostMapping("login")
    public Result login(@Validated @RequestBody LoginBody body, HttpSession session) {
        String code = (String) session.getAttribute(CacheConstant.KAPTCHA_IMG_CODE);
        Long time = (Long) session.getAttribute(CacheConstant.KAPTCHA_IMG_TIME);

        session.removeAttribute(CacheConstant.KAPTCHA_IMG_CODE);
        session.removeAttribute(CacheConstant.KAPTCHA_IMG_TIME);

        String msg = "登录成功";
        boolean status = false;
        try {
            if (time == null || System.currentTimeMillis() - time > TimeConstant.KAPTCHA_IMG_EXIST_TIME || !body.getKaptcha().equals(code)) {
                throw new IllegalArgumentException();
            }
            UsernamePasswordToken token = new UsernamePasswordToken(body.getAccount(), body.getPassword(), body.getRememberMe());
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            status = true;
            return Result.ok();
        } catch (IllegalArgumentException e) {
            msg = "验证码错误";
            throw new BusinessException(ResultStatus.VERIFICATION_CODE_ERROR);
        } catch (UnknownAccountException e) {
            msg = "用户名错误";
            throw new BusinessException(ResultStatus.INCORRECT_ACCOUNT_OR_PASSWORD);
        } catch (CredentialsException e) {
            msg = "密码错误";
            throw new BusinessException(ResultStatus.INCORRECT_ACCOUNT_OR_PASSWORD);
        } catch (LockedAccountException e) {
            msg = "账号锁定";
            throw new BusinessException(ResultStatus.ACCOUNT_LOCKED);
        } catch (Exception e) {
            msg = e.getMessage();
            throw e;
        } finally {
            this.addLoginLog(body.getAccount(), status, msg);
        }
    }

    /**
     * 用户登出
     *
     * @return Result
     */
    @GetMapping("logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.ok();
    }

    /**
     * 修改密码
     *
     * @param body body
     * @return Result
     */
    @PutMapping("change_pwd")
    public Result changePwd(@Validated @RequestBody ChangePwdBody body) {
        // 校验两次新密码是否相同
        Assert.isTrue(body.getPassword().equals(body.getPassword2()), ResultStatus.PASSWORD_ERROR);

        // 校验旧密码是否相同
        Long uid = ShiroUtil.getUid();
        Wrapper<SysUser> wrapper = new LambdaQueryWrapper<>(SysUser.class)
                .select(field -> true)
                .eq(SysUser::getUid, uid)
                .eq(SysUser::getIsDeleted, false);
        SysUser user = sysUserService.getOne(wrapper);
        Assert.isTrue(BCrypt.checkpw(body.getOldPassword(), user.getPassword()), ResultStatus.PASSWORD_ERROR);

        // 修改密码
        SysUser modifyUser = new SysUser()
                .setUid(uid)
                .setPassword(BCrypt.hashpw(body.getPassword(), BCrypt.gensalt()));
        sysUserService.updateByUid(modifyUser);

        // 修改成功退出登陆
        SecurityUtils.getSubject().logout();
        return Result.ok();
    }


    /**
     * 判断是否登陆，如果未登陆shiro会拦截，如果正常返回则说明登陆过了
     * <p>
     * 前端大部分路由跳转都要访问此接口（路由前置守卫），设置cookie后，前端先判断cookie是否存在，不存在再访问此接口
     * 如果cookie存在，session过期，会由前端的axios全局拦截跳转到login页面
     * <p>
     * cookie存活时间半小时
     *
     * @return Result
     */
    @GetMapping("is_login")
    public Result isLogin() {
        WebUtil.setHeader("Set-Cookie", "login=login; path=/; Max-Age=1800");
        return Result.ok();
    }

    /**
     * 添加登陆日志
     *
     * @param account 登陆的账号
     * @param status  登陆的状态
     * @param msg     登陆的信息
     */
    private void addLoginLog(String account, boolean status, String msg) {
        String ip = WebUtil.getIp();
        // 获取浏览器信息（操作系统，浏览器）
        UserAgent userAgent = UserAgent.parseUserAgentString(WebUtil.getHeader(HttpHeaders.USER_AGENT));
        String os = userAgent.getOperatingSystem().getName();
        String browser = userAgent.getBrowser().getName();

        SysLoginLog log = new SysLoginLog()
                .setIp(ip)
                .setOs(os)
                .setBrowser(browser)
                .setAccount(account.toLowerCase())
                .setStatus(status)
                .setMsg(msg);
        simpleAsyncTaskExecutor.execute(() -> {
            sysLoginLogService.add(log);
        });
    }
}
