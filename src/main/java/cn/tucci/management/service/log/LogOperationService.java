package cn.tucci.management.service.log;

import cn.tucci.management.model.domain.log.LogOperation;
import cn.tucci.management.model.query.LogOperationQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author tucci.lee
 */
public interface LogOperationService {
    int add(LogOperation operationLog);

    Page<LogOperation> list(LogOperationQuery query);
}
