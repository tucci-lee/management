package cn.tucci.management.service.sys.impl;

import cn.tucci.management.model.dao.sys.SysOperationLogMapper;
import cn.tucci.management.model.domain.sys.SysOperationLog;
import cn.tucci.management.model.query.OperationLogQuery;
import cn.tucci.management.service.sys.SysOperationLogService;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tucci.lee
 */
@Service
public class SysOperationLogServiceImpl implements SysOperationLogService {

    private final SysOperationLogMapper sysOperationLogMapper;

    @Autowired
    public SysOperationLogServiceImpl(SysOperationLogMapper sysOperationLogMapper) {
        this.sysOperationLogMapper = sysOperationLogMapper;
    }

    @Override
    public int add(SysOperationLog operationLog) {
        return sysOperationLogMapper.insert(operationLog);
    }

    @Override
    public Page<SysOperationLog> list(OperationLogQuery query) {
        Page<SysOperationLog> page = new Page<SysOperationLog>()
                .setCurrent(query.getPageNum())
                .addOrder(new OrderItem(query.getColumn(), query.getAsc()));

        return sysOperationLogMapper.selectPage(page, query);
    }
}
