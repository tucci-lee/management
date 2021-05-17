package cn.tucci.management.service.monitor;

import cn.tucci.management.model.dto.TaskStartLogDTO;
import cn.tucci.management.model.query.TaskStartLogQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author tucci.lee
 */
public interface TaskStartLogService {
    Page<TaskStartLogDTO> list(TaskStartLogQuery query);
}
