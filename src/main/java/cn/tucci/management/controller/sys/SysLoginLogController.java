package cn.tucci.management.controller.sys;

import cn.tucci.management.core.response.PageResult;
import cn.tucci.management.core.response.Result;
import cn.tucci.management.model.domain.sys.SysLoginLog;
import cn.tucci.management.model.query.LoginLogQuery;
import cn.tucci.management.service.sys.SysLoginLogService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tucci.lee
 */
@RestController
@RequestMapping("/log/login")
public class SysLoginLogController {

    private final SysLoginLogService sysLoginLogService;

    @Autowired
    public SysLoginLogController(SysLoginLogService sysLoginLogService) {
        this.sysLoginLogService = sysLoginLogService;
    }

    /**
     * 登陆日志查询
     *
     * @param query query
     * @return Result
     */
    @RequiresPermissions(value = {"log:login:list"})
    @GetMapping
    public Result list(@Validated LoginLogQuery query) {
        Page<SysLoginLog> page = sysLoginLogService.list(query);
        return PageResult.ok(page.getRecords(), page.getTotal());
    }
}
