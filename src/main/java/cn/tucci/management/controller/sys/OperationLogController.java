package cn.tucci.management.controller.sys;

import cn.tucci.management.core.response.PageResult;
import cn.tucci.management.core.response.Result;
import cn.tucci.management.model.domain.sys.SysOperationLog;
import cn.tucci.management.model.query.OperationLogQuery;
import cn.tucci.management.service.sys.SysOperationLogService;
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
@RequestMapping("/log/operation")
public class OperationLogController {

    private final SysOperationLogService sysOperationLogService;

    @Autowired
    public OperationLogController(SysOperationLogService sysOperationLogService) {
        this.sysOperationLogService = sysOperationLogService;
    }

    /**
     * 操作日志查询
     *
     * @param query query
     * @return Result
     */
    @RequiresPermissions(value = {"log:operation:list"})
    @GetMapping
    public Result list(@Validated OperationLogQuery query) {
        Page<SysOperationLog> page = sysOperationLogService.list(query);
        return PageResult.ok(page.getRecords(), page.getTotal());
    }
}
