package cn.tucci.management.service.log.impl;

import cn.tucci.management.model.dao.log.LogOperationMapper;
import cn.tucci.management.model.domain.log.LogOperation;
import cn.tucci.management.model.query.LogOperationQuery;
import cn.tucci.management.service.log.LogOperationService;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tucci.lee
 */
@Service
public class LogOperationServiceImpl implements LogOperationService {

    private final LogOperationMapper logOperationMapper;

    @Autowired
    public LogOperationServiceImpl(LogOperationMapper logOperationMapper) {
        this.logOperationMapper = logOperationMapper;
    }

    @Override
    public int add(LogOperation operationLog) {
        return logOperationMapper.insert(operationLog);
    }

    @Override
    public Page<LogOperation> list(LogOperationQuery query) {
        Page<LogOperation> page = new Page<LogOperation>()
                .setCurrent(query.getPageNum())
                .addOrder(new OrderItem(query.getColumn(), query.getAsc()));

        return logOperationMapper.selectPage(page, query);
    }
}
