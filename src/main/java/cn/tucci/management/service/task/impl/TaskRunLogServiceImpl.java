package cn.tucci.management.service.task.impl;

import cn.tucci.management.model.dao.task.TaskRunLogMapper;
import cn.tucci.management.model.domain.task.TaskRunLog;
import cn.tucci.management.model.query.TaskRunLogQuery;
import cn.tucci.management.service.task.TaskRunLogService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tucci.lee
 */
@Service
public class TaskRunLogServiceImpl implements TaskRunLogService {
    private final TaskRunLogMapper taskRunLogMapper;

    @Autowired
    public TaskRunLogServiceImpl(TaskRunLogMapper taskRunLogMapper) {
        this.taskRunLogMapper = taskRunLogMapper;
    }

    @Override
    public Page<TaskRunLog> list(TaskRunLogQuery query) {
        // 分页+排序
        Page<TaskRunLog> page = new Page<TaskRunLog>()
                .setCurrent(query.getPageNum())
                .addOrder(new OrderItem(query.getColumn(), query.getAsc()));

        Wrapper<TaskRunLog> wrapper = new LambdaQueryWrapper<>(TaskRunLog.class)
                .eq(TaskRunLog::getTaskScheduleId, query.getTaskId());
        return taskRunLogMapper.selectPage(page, wrapper);
    }
}
