package cn.tucci.management.controller.log;

import cn.tucci.management.core.response.PageResult;
import cn.tucci.management.core.response.Result;
import cn.tucci.management.model.domain.log.LogLogin;
import cn.tucci.management.model.query.LogLoginQuery;
import cn.tucci.management.service.log.LogLoginService;
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
public class LogLoginController {

    private final LogLoginService logLoginService;

    @Autowired
    public LogLoginController(LogLoginService logLoginService) {
        this.logLoginService = logLoginService;
    }

    /**
     * 登陆日志查询
     *
     * @param query query
     * @return Result
     */
    @RequiresPermissions(value = {"log:login:list"})
    @GetMapping
    public Result list(@Validated LogLoginQuery query) {
        Page<LogLogin> page = logLoginService.list(query);
        return PageResult.ok(page.getRecords(), page.getTotal());
    }
}
