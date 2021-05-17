package cn.tucci.management.service.log;

import cn.tucci.management.model.domain.log.LogLogin;
import cn.tucci.management.model.query.LogLoginQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author tucci.lee
 */
public interface LogLoginService {
    int add(LogLogin log);

    Page<LogLogin> list(LogLoginQuery query);
}
