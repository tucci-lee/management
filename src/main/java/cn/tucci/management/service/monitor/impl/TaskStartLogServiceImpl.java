package cn.tucci.management.service.monitor.impl;

import cn.tucci.management.model.dao.monitor.TaskStartLogMapper;
import cn.tucci.management.model.domain.monitor.TaskStartLog;
import cn.tucci.management.model.dto.TaskStartLogDTO;
import cn.tucci.management.model.query.TaskStartLogQuery;
import cn.tucci.management.service.monitor.TaskStartLogService;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tucci.lee
 */
@Service
public class TaskStartLogServiceImpl implements TaskStartLogService {

    private final TaskStartLogMapper taskStartLogMapper;

    @Autowired
    public TaskStartLogServiceImpl(TaskStartLogMapper taskStartLogMapper) {
        this.taskStartLogMapper = taskStartLogMapper;
    }

    @Override
    public Page<TaskStartLogDTO> list(TaskStartLogQuery query) {
        // 分页+排序
        Page<TaskStartLog> page = new Page<TaskStartLog>()
                .setCurrent(query.getPageNum())
                .addOrder(new OrderItem(query.getColumn(), query.getAsc()));

        return taskStartLogMapper.selectList(page, query);
    }
}
