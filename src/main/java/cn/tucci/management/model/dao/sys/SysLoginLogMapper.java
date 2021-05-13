package cn.tucci.management.model.dao.sys;

import cn.tucci.management.model.domain.sys.SysLoginLog;
import cn.tucci.management.model.query.LoginLogQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

public interface SysLoginLogMapper extends BaseMapper<SysLoginLog> {

    Page<SysLoginLog> selectPage(Page<SysLoginLog> page, @Param("query") LoginLogQuery query);

}