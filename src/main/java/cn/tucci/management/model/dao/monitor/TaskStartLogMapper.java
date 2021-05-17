package cn.tucci.management.model.dao.monitor;

import cn.tucci.management.model.domain.monitor.TaskStartLog;
import cn.tucci.management.model.dto.TaskStartLogDTO;
import cn.tucci.management.model.query.TaskStartLogQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

public interface TaskStartLogMapper extends BaseMapper<TaskStartLog> {

    Page<TaskStartLogDTO> selectList(Page<?> page, @Param("query") TaskStartLogQuery query);
}