package cn.tucci.management.service.sys;

import cn.tucci.management.model.domain.sys.SysLoginLog;
import cn.tucci.management.model.query.LoginLogQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author tucci.lee
 */
public interface SysLoginLogService {
    int add(SysLoginLog log);

    Page<SysLoginLog> list(LoginLogQuery query);
}
