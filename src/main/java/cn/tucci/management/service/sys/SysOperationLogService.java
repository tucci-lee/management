package cn.tucci.management.service.sys;

import cn.tucci.management.model.domain.sys.SysOperationLog;
import cn.tucci.management.model.query.OperationLogQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author tucci.lee
 */
public interface SysOperationLogService {
    int add(SysOperationLog operationLog);

    Page<SysOperationLog> list(OperationLogQuery query);
}
