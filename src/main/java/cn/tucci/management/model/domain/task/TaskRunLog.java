package cn.tucci.management.model.domain.task;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@TableName("task_run_log")
public class TaskRunLog implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long taskScheduleId;

    private Boolean status;

    private String msg;

    private Long runTime;

    private Long createTime;

    private static final long serialVersionUID = 5296120782595570023L;
}