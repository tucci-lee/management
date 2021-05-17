package cn.tucci.management.controller.log;

import cn.tucci.management.core.response.PageResult;
import cn.tucci.management.core.response.Result;
import cn.tucci.management.model.domain.log.LogOperation;
import cn.tucci.management.model.query.LogOperationQuery;
import cn.tucci.management.service.log.LogOperationService;
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
public class LogOperationController {

    private final LogOperationService logOperationService;

    @Autowired
    public LogOperationController(LogOperationService logOperationService) {
        this.logOperationService = logOperationService;
    }

    /**
     * 操作日志查询
     *
     * @param query query
     * @return Result
     */
    @RequiresPermissions(value = {"log:operation:list"})
    @GetMapping
    public Result list(@Validated LogOperationQuery query) {
        Page<LogOperation> page = logOperationService.list(query);
        return PageResult.ok(page.getRecords(), page.getTotal());
    }
}
