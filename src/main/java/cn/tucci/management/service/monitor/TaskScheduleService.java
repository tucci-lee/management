package cn.tucci.management.service.monitor;

import cn.tucci.management.model.domain.monitor.TaskSchedule;
import cn.tucci.management.model.dto.TaskScheduleDTO;
import cn.tucci.management.model.query.TaskScheduleQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author tucci.lee
 */
public interface TaskScheduleService {

    /**
     * 启动系统定时任务
     */
    void startSysTask();

    /**
     * 分页查询定时任务
     *
     * @param query query
     * @return TaskScheduleList
     */
    Page<TaskScheduleDTO> list(TaskScheduleQuery query);

    /**
     * 添加定时任务
     *
     * @param task task
     * @return int
     */
    int add(TaskSchedule task);

    /**
     * 修改定时任务
     *
     * @param task task
     * @return int
     */
    int edit(TaskSchedule task);

    /**
     * 删除定时任务
     *
     * @param task task
     * @return int
     */
    int delete(TaskSchedule task);

    /**
     * 编辑定时任务状态 （启动或停止）
     *
     * @param id     任务id
     * @param status 任务状态
     * @param uid    用户id
     */
    void editStatus(Long id, boolean status, Long uid);
}
