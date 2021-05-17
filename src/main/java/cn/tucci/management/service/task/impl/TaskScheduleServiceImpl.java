package cn.tucci.management.service.task.impl;

import cn.tucci.management.core.task.AbstractTask;
import cn.tucci.management.model.dao.task.TaskScheduleMapper;
import cn.tucci.management.model.dao.task.TaskStartLogMapper;
import cn.tucci.management.model.domain.task.TaskSchedule;
import cn.tucci.management.model.domain.task.TaskStartLog;
import cn.tucci.management.model.dto.TaskScheduleDTO;
import cn.tucci.management.model.query.TaskScheduleQuery;
import cn.tucci.management.service.task.TaskScheduleService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

/**
 * @author tucci.lee
 */
@Service
public class TaskScheduleServiceImpl implements TaskScheduleService {

    private final TaskScheduleMapper taskScheduleMapper;
    private final TaskStartLogMapper taskStartLogMapper;
    private final Map<Long, ScheduledFuture<?>> scheduledFutureMap;
    private final ApplicationContext applicationContext;
    private final TaskScheduler taskScheduler;

    @Autowired
    public TaskScheduleServiceImpl(TaskScheduleMapper taskScheduleMapper, TaskStartLogMapper taskStartLogMapper, Map<Long, ScheduledFuture<?>> scheduledFutureMap, ApplicationContext applicationContext, TaskScheduler taskScheduler) {
        this.taskScheduleMapper = taskScheduleMapper;
        this.taskStartLogMapper = taskStartLogMapper;
        this.scheduledFutureMap = scheduledFutureMap;
        this.applicationContext = applicationContext;
        this.taskScheduler = taskScheduler;
    }

    @Override
    public void startSysTask() {
        // 查询所有系统任务
        Wrapper<TaskSchedule> wrapper = new LambdaQueryWrapper<>(TaskSchedule.class)
                .eq(TaskSchedule::getType, TaskSchedule.Type.SYS)
                .eq(TaskSchedule::getIsDeleted, Boolean.FALSE);
        List<TaskSchedule> taskSchedules = taskScheduleMapper.selectList(wrapper);
        this.startTask(0L, taskSchedules.toArray(new TaskSchedule[1]));
    }

    @Override
    public Page<TaskScheduleDTO> list(TaskScheduleQuery query) {
        Page<TaskSchedule> page = new Page<TaskSchedule>()
                .setCurrent(query.getPageNum())
                .addOrder(new OrderItem(query.getColumn(), query.getAsc()));

        query.setIds(scheduledFutureMap.keySet());

        Page<TaskScheduleDTO> resultPage = taskScheduleMapper.selectPage(page, query);
        resultPage.getRecords().forEach(e -> {
            if (scheduledFutureMap.get(e.getId()) != null) {
                e.setStatus(Boolean.TRUE);
                return;
            }
            e.setStatus(Boolean.FALSE);
        });
        return resultPage;
    }

    @Override
    public int add(TaskSchedule task) {
        return taskScheduleMapper.insert(task);
    }

    @Override
    public int edit(TaskSchedule task) {
        return taskScheduleMapper.updateById(task);
    }

    @Override
    public int delete(TaskSchedule task) {
        return taskScheduleMapper.updateById(task);
    }

    @Override
    public void editStatus(Long id, boolean status, Long uid) {
        // 如果是启动
        if (status) {
            Wrapper<TaskSchedule> wrapper = new LambdaQueryWrapper<>(TaskSchedule.class)
                    .eq(TaskSchedule::getId, id)
                    .eq(TaskSchedule::getIsDeleted, Boolean.FALSE);
            TaskSchedule taskSchedule = taskScheduleMapper.selectOne(wrapper);
            if(taskSchedule != null) {
                this.startTask(uid, taskSchedule);
            }
        } else {
            // 如果是停止
            ScheduledFuture<?> future = scheduledFutureMap.get(id);
            if (future != null) {
                future.cancel(Boolean.TRUE);
                scheduledFutureMap.remove(id);
            }
        }
    }

    /**
     * 启动定时任务
     *
     * @param uid           操作人的id
     * @param taskSchedules 任务
     */
    protected void startTask(Long uid, TaskSchedule... taskSchedules) {
        for (TaskSchedule taskSchedule : taskSchedules) {
            // 启动日志
            TaskStartLog log = new TaskStartLog()
                    .setTaskScheduleId(taskSchedule.getId())
                    .setCreator(uid);
            String className = taskSchedule.getClassName();
            try {
                // 加载定时任务bean
                Class<?> taskClass = Class.forName(className);
                Object taskBean = applicationContext.getBean(taskClass);
                if (taskBean instanceof AbstractTask) {
                    AbstractTask task = (AbstractTask) taskBean;
                    task.setTaskScheduleId(taskSchedule.getId());
                    ScheduledFuture<?> future = taskScheduler.schedule(task, new CronTrigger(taskSchedule.getCron()));
                    scheduledFutureMap.put(taskSchedule.getId(), future);

                    log.setMsg("ok")
                            .setStatus(Boolean.TRUE);
                } else {
                    throw new ClassCastException("不是一个正确的任务");
                }
            } catch (Exception e) {
                log.setMsg(e.getMessage())
                        .setStatus(Boolean.FALSE);
            } finally {
                // 添加启动日志
                taskStartLogMapper.insert(log);
            }
        }
    }
}
