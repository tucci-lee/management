package cn.tucci.management.controller.sys;

import cn.tucci.management.core.annotation.Log;
import cn.tucci.management.core.response.PageResult;
import cn.tucci.management.core.response.Result;
import cn.tucci.management.core.util.BCrypt;
import cn.tucci.management.core.util.RootUtil;
import cn.tucci.management.model.body.UserAddBody;
import cn.tucci.management.model.body.LockEditBody;
import cn.tucci.management.model.body.UserPwdEditBody;
import cn.tucci.management.model.body.UserEditBody;
import cn.tucci.management.model.body.UserRoleEditBody;
import cn.tucci.management.model.domain.sys.SysUser;
import cn.tucci.management.model.query.UserQuery;
import cn.tucci.management.service.sys.SysUserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tucci.lee
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    private final SysUserService sysUserService;

    @Autowired
    public SysUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    /**
     * 分页查询用户
     *
     * @param query query
     * @return Result
     */
    @RequiresPermissions(value = {"sys:user:list"})
    @GetMapping
    public Result list(@Validated UserQuery query) {
        Page<SysUser> page = sysUserService.list(query);
        return PageResult.ok(page.getRecords(), page.getTotal());
    }

    /**
     * 添加用户
     *
     * @param body body
     * @return Result
     */
    @Log("添加用户")
    @RequiresPermissions(value = {"sys:user:add"})
    @PostMapping
    public Result add(@Validated @RequestBody UserAddBody body) {
        SysUser user = new SysUser();
        BeanUtils.copyProperties(body, user);

        // 账号设置为全小写
        user.setAccount(user.getAccount().toLowerCase());
        // 密码加密
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));

        sysUserService.add(user, body.getRoleIds());
        return Result.ok();
    }

    /**
     * 修改用户基础信息
     *
     * @param body body
     * @return Result
     */
    @Log("修改用户")
    @RequiresPermissions(value = {"sys:user:edit"})
    @PutMapping
    public Result edit(@Validated @RequestBody UserEditBody body) {
        RootUtil.isRoot(body.getUid());

        SysUser user = new SysUser();
        BeanUtils.copyProperties(body, user);

        sysUserService.updateByUid(user);
        return Result.ok();
    }

    /**
     * 修改用户锁定状态
     *
     * @param body body
     * @return Result
     */
    @Log("修改用户锁定状态")
    @RequiresPermissions(value = {"sys:user:editLock"})
    @PutMapping("edit_lock")
    public Result editLock(@Validated @RequestBody LockEditBody body) {
        RootUtil.notEditRoot(body.getUid());

        SysUser user = new SysUser();
        BeanUtils.copyProperties(body, user);
        sysUserService.updateByUid(user);
        return Result.ok();
    }

    /**
     * 修改用户密码
     *
     * @param body body
     * @return Result
     */
    @Log(value = "修改用户密码", recordParams = false)
    @RequiresPermissions(value = {"sys:user:editPwd"})
    @PutMapping("edit_pwd")
    public Result editPwd(@Validated @RequestBody UserPwdEditBody body) {
        RootUtil.isRoot(body.getUid());

        SysUser user = new SysUser()
                .setUid(body.getUid())
                .setPassword(BCrypt.hashpw(body.getPassword(), BCrypt.gensalt()));
        sysUserService.updateByUid(user);
        return Result.ok();
    }

    /**
     * 修改用户关联的角色
     *
     * @param body body
     * @return Result
     */
    @Log("修改用户关联的角色")
    @RequiresPermissions(value = {"sys:user:edit"})
    @PutMapping("edit_role")
    public Result editRole(@Validated @RequestBody UserRoleEditBody body) {
        RootUtil.isRoot(body.getUid());

        sysUserService.editRole(body.getUid(), body.getRoleIds());
        return Result.ok();
    }

    /**
     * 删除用户
     * @param uid
     * @return
     */
    @Log("删除用户")
    @RequiresPermissions(value = {"sys:user:delete"})
    @DeleteMapping("{uid}")
    public Result delete(@PathVariable Long uid) {
        RootUtil.notEditRoot(uid);

        SysUser user = new SysUser()
                .setUid(uid)
                .setIsDeleted(true);
        sysUserService.updateByUid(user);
        return Result.ok();
    }
}
