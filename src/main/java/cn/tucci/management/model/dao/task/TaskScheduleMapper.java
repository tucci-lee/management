package cn.tucci.management.model.dao.task;

import cn.tucci.management.model.domain.task.TaskSchedule;
import cn.tucci.management.model.dto.TaskScheduleDTO;
import cn.tucci.management.model.query.TaskScheduleQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

public interface TaskScheduleMapper extends BaseMapper<TaskSchedule> {

    Page<TaskScheduleDTO> selectPage(Page<TaskSchedule> page, @Param("query") TaskScheduleQuery query);
}