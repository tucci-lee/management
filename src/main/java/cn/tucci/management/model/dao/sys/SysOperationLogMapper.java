package cn.tucci.management.model.dao.sys;

import cn.tucci.management.model.domain.sys.SysOperationLog;
import cn.tucci.management.model.query.OperationLogQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

public interface SysOperationLogMapper extends BaseMapper<SysOperationLog> {

    Page<SysOperationLog> selectPage(Page<SysOperationLog> page, @Param("query") OperationLogQuery query);

}