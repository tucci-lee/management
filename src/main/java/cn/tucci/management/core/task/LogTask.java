package cn.tucci.management.core.task;

import cn.tucci.management.model.dao.monitor.TaskRunLogMapper;
import cn.tucci.management.model.domain.monitor.TaskRunLog;

/**
 * @author tucci.lee
 */
public abstract class LogTask extends AbstractTask{

    private final TaskRunLogMapper taskRunLogMapper;

    protected LogTask(TaskRunLogMapper taskRunLogMapper) {
        this.taskRunLogMapper = taskRunLogMapper;
    }

    @Override
    protected void end(boolean status, long startTime, String msg) {
        TaskRunLog log = new TaskRunLog()
                .setTaskScheduleId(getTaskScheduleId())
                .setStatus(status)
                .setMsg(msg)
                .setRunTime(System.currentTimeMillis() - startTime)
                .setCreateTime(startTime);
        taskRunLogMapper.insert(log);
    }
}
