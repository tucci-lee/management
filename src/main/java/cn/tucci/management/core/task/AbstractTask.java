package cn.tucci.management.core.task;

import javax.validation.constraints.NotNull;

/**
 * @author tucci.lee
 */
public abstract class AbstractTask implements Runnable {
    @NotNull
    private Long taskScheduleId;

    @Override
    public void run() {
        if (taskScheduleId == null) {
            return;
        }
        boolean status = true;
        String msg = null;
        long startTime = System.currentTimeMillis();
        try {
            msg = this.execute();
            long endTime = System.currentTimeMillis();
        } catch (Throwable e) {
            status = false;
            msg = e.getMessage();
        } finally {
            this.end(status, startTime, msg);
        }
    }

    /**
     * 执行任务的逻辑，返回执行信息。
     *
     * @return msg 执行信息
     */
    protected abstract String execute();

    /**
     * 执行任务结束
     *
     * @param status    执行状态
     * @param startTime 任务开始执行时间内
     * @param msg       任务执行返回的信息
     */
    protected void end(boolean status, long startTime, String msg) {
    }

    /**
     * 不设置定时任务id，会跳过执行
     *
     * @param taskScheduleId taskScheduleId
     */
    public void setTaskScheduleId(Long taskScheduleId) {
        this.taskScheduleId = taskScheduleId;
    }

    public Long getTaskScheduleId() {
        return this.taskScheduleId;
    }
}
