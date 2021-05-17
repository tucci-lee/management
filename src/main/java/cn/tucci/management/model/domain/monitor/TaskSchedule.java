package cn.tucci.management.model.domain.monitor;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@TableName("task_schedule")
public class TaskSchedule implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String name;

    private String className;

    private String cron;

    private Integer type;

    private String remarks;

    private Long creator;

    @TableField(fill = FieldFill.INSERT)
    private Long createTime;

    private Long updater;

    @TableField(fill = FieldFill.UPDATE)
    private Long updatedTime;

    @TableField(select = false)
    private Boolean isDeleted;

    private static final long serialVersionUID = -7521759046275182179L;

    public interface Type{
        int SYS = 1;
        int MANUAL = 2;
    }
}