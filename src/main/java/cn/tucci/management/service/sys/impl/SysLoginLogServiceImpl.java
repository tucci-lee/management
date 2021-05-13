package cn.tucci.management.service.sys.impl;

import cn.tucci.management.model.dao.sys.SysLoginLogMapper;
import cn.tucci.management.model.domain.sys.SysLoginLog;
import cn.tucci.management.model.query.LoginLogQuery;
import cn.tucci.management.service.sys.SysLoginLogService;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tucci.lee
 */
@Service
public class SysLoginLogServiceImpl implements SysLoginLogService {
    private final SysLoginLogMapper sysLoginLogMapper;

    @Autowired
    public SysLoginLogServiceImpl(SysLoginLogMapper sysLoginLogMapper) {
        this.sysLoginLogMapper = sysLoginLogMapper;
    }

    @Override
    public int add(SysLoginLog log) {
        return sysLoginLogMapper.insert(log);
    }

    @Override
    public Page<SysLoginLog> list(LoginLogQuery query) {
        Page<SysLoginLog> page = new Page<SysLoginLog>()
                .setCurrent(query.getPageNum())
                .addOrder(new OrderItem(query.getColumn(), query.getAsc()));

        return sysLoginLogMapper.selectPage(page, query);
    }
}
