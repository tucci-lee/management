package cn.tucci.management.model.dao.log;

import cn.tucci.management.model.domain.log.LogOperation;
import cn.tucci.management.model.query.LogOperationQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

public interface LogOperationMapper extends BaseMapper<LogOperation> {

    Page<LogOperation> selectPage(Page<LogOperation> page, @Param("query") LogOperationQuery query);

}