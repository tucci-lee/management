package cn.tucci.management.service.task;

import cn.tucci.management.model.domain.task.TaskRunLog;
import cn.tucci.management.model.query.TaskRunLogQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author tucci.lee
 */
public interface TaskRunLogService {
    Page<TaskRunLog> list(TaskRunLogQuery query);
}
