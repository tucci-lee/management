package cn.tucci.management.core.aspect;

import cn.tucci.management.core.annotation.Log;
import cn.tucci.management.core.util.WebUtil;
import cn.tucci.management.model.domain.sys.SysOperationLog;
import cn.tucci.management.model.domain.sys.SysUser;
import cn.tucci.management.service.sys.SysOperationLogService;
import cn.tucci.management.shiro.util.ShiroUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * 操作日志记录
 */
@Aspect
@Component
public class LogAspect {
    private final SimpleAsyncTaskExecutor simpleAsyncTaskExecutor;
    private final SysOperationLogService sysOperationLogService;

    @Autowired
    public LogAspect(SimpleAsyncTaskExecutor simpleAsyncTaskExecutor, SysOperationLogService sysOperationLogService) {
        this.simpleAsyncTaskExecutor = simpleAsyncTaskExecutor;
        this.sysOperationLogService = sysOperationLogService;
    }

    @Pointcut("@annotation(log)")
    public void pointcut(Log log) {
    }

    @Around("pointcut(log)")
    public Object around(ProceedingJoinPoint pjp, Log log) throws Throwable {
        long beginTime = System.currentTimeMillis();

        Signature sig = pjp.getSignature();
        String method = pjp.getTarget().getClass().getName() + "." + sig.getName();
        String value = log.value();
        String url = WebUtil.getRequest().getRequestURL().toString();
        SysUser user = ShiroUtil.getUser();
        String ip = WebUtil.getIp();

        //获取参数, 只获取第一个参数
        String params = null;
        if(log.recordParams()) {
            Object[] args = pjp.getArgs();
            params = JSON.toJSONString(args[0]);
        }

        SysOperationLog operationLog = new SysOperationLog()
                .setUid(user.getUid())
                .setAccount(user.getAccount())
                .setIp(ip)
                .setUrl(url)
                .setMethod(method)
                .setParams(params)
                .setDescription(value);
        try {
            Object result = pjp.proceed();
            add(operationLog, JSONObject.toJSONString(result), null, true);
            return result;
        } catch (Throwable e) {
            add(operationLog, null, e.getMessage(), false);
            throw e;
        }
    }

    /**
     * 添加操作日志
     *
     * @param operationLog
     * @param result
     * @param errMsg
     * @param status
     */
    private void add(SysOperationLog operationLog, String result, String errMsg, boolean status) {
        operationLog.setResult(result).setStatus(status)
                .setErrMsg(errMsg);
        simpleAsyncTaskExecutor.execute(() -> sysOperationLogService.add(operationLog));
    }
}
