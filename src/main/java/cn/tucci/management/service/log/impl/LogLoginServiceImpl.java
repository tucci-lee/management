package cn.tucci.management.service.log.impl;

import cn.tucci.management.model.dao.log.LogLoginMapper;
import cn.tucci.management.model.domain.log.LogLogin;
import cn.tucci.management.model.query.LogLoginQuery;
import cn.tucci.management.service.log.LogLoginService;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tucci.lee
 */
@Service
public class LogLoginServiceImpl implements LogLoginService {
    private final LogLoginMapper logLoginMapper;

    @Autowired
    public LogLoginServiceImpl(LogLoginMapper logLoginMapper) {
        this.logLoginMapper = logLoginMapper;
    }

    @Override
    public int add(LogLogin log) {
        return logLoginMapper.insert(log);
    }

    @Override
    public Page<LogLogin> list(LogLoginQuery query) {
        Page<LogLogin> page = new Page<LogLogin>()
                .setCurrent(query.getPageNum())
                .addOrder(new OrderItem(query.getColumn(), query.getAsc()));

        return logLoginMapper.selectPage(page, query);
    }
}
