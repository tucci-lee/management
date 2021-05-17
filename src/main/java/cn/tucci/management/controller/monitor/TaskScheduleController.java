package cn.tucci.management.controller.monitor;

import cn.tucci.management.core.annotation.Log;
import cn.tucci.management.core.response.PageResult;
import cn.tucci.management.core.response.Result;
import cn.tucci.management.model.body.TaskScheduleAddBody;
import cn.tucci.management.model.body.TaskScheduleEditBody;
import cn.tucci.management.model.body.TaskScheduleEditStatusBody;
import cn.tucci.management.model.domain.task.TaskRunLog;
import cn.tucci.management.model.domain.task.TaskSchedule;
import cn.tucci.management.model.dto.TaskScheduleDTO;
import cn.tucci.management.model.dto.TaskStartLogDTO;
import cn.tucci.management.model.query.TaskRunLogQuery;
import cn.tucci.management.model.query.TaskScheduleQuery;
import cn.tucci.management.model.query.TaskStartLogQuery;
import cn.tucci.management.service.task.TaskRunLogService;
import cn.tucci.management.service.task.TaskScheduleService;
import cn.tucci.management.service.task.TaskStartLogService;
import cn.tucci.management.shiro.util.ShiroUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tucci.lee
 */
@RestController
@RequestMapping("/monitor/task")
public class TaskScheduleController {

    private final TaskScheduleService taskScheduleService;
    private final TaskStartLogService taskStartLogService;
    private final TaskRunLogService taskRunLogService;

    @Autowired
    public TaskScheduleController(TaskScheduleService taskScheduleService, TaskStartLogService taskStartLogService, TaskRunLogService taskRunLogService) {
        this.taskScheduleService = taskScheduleService;
        this.taskStartLogService = taskStartLogService;
        this.taskRunLogService = taskRunLogService;
    }

    /**
     * 分页查询定时任务
     *
     * @param query query
     * @return Result
     */
    @RequiresPermissions(value = {"monitor:task:list"})
    @GetMapping
    public Result list(@Validated TaskScheduleQuery query) {
        Page<TaskScheduleDTO> page = taskScheduleService.list(query);
        return PageResult.ok(page.getRecords(), page.getTotal());
    }

    /**
     * 添加定时任务
     *
     * @param body body
     * @return Result
     */
    @RequiresPermissions(value = {"monitor:task:add"})
    @Log("添加定时任务")
    @PostMapping
    public Result add(@Validated @RequestBody TaskScheduleAddBody body) {
        TaskSchedule task = new TaskSchedule()
                .setCreator(ShiroUtil.getUid());

        BeanUtils.copyProperties(body, task);
        taskScheduleService.add(task);
        return Result.ok();
    }

    /**
     * 修改定时任务
     *
     * @param body body
     * @return Result
     */
    @RequiresPermissions(value = {"monitor:task:edit"})
    @Log("修改定时任务")
    @PutMapping
    public Result edit(@Validated @RequestBody TaskScheduleEditBody body) {
        TaskSchedule task = new TaskSchedule()
                .setUpdater(ShiroUtil.getUid());

        BeanUtils.copyProperties(body, task);
        taskScheduleService.edit(task);
        return Result.ok();
    }

    /**
     * 删除定时任务
     *
     * @param id id
     * @return Reuslt
     */
    @RequiresPermissions(value = {"monitor:task:delete"})
    @Log("删除定时任务")
    @DeleteMapping("{id}")
    public Result delete(@PathVariable Long id) {
        TaskSchedule task = new TaskSchedule()
                .setId(id)
                .setUpdater(ShiroUtil.getUid())
                .setIsDeleted(Boolean.TRUE);
        taskScheduleService.delete(task);
        return Result.ok();
    }


    @RequiresPermissions(value = {"monitor:task:editStatus"})
    @Log("编辑定时任务状态")
    @PutMapping("edit_status")
    public Result editStatus(@Validated @RequestBody TaskScheduleEditStatusBody body) {

        taskScheduleService.editStatus(body.getId(), body.getStatus(), ShiroUtil.getUid());
        return Result.ok();
    }

    /**
     * 分页查询启动日志
     *
     * @param query query
     * @return Result
     */
    @RequiresPermissions(value = {"monitor:task:list"})
    @GetMapping("/start/log")
    public Result startLog(@Validated TaskStartLogQuery query) {
        Page<TaskStartLogDTO> page = taskStartLogService.list(query);
        return PageResult.ok(page.getRecords(), page.getTotal());
    }

    /**
     * 分页查询运行日志
     * @param query query
     * @return Result
     */
    @RequiresPermissions(value = {"monitor:task:list"})
    @GetMapping("/run/log")
    public Result runLog(@Validated TaskRunLogQuery query) {
        Page<TaskRunLog> page = taskRunLogService.list(query);
        return PageResult.ok(page.getRecords(), page.getTotal());
    }
}
