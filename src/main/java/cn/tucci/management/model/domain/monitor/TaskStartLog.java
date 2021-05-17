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
@TableName("task_start_log")
public class TaskStartLog implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long taskScheduleId;

    private Boolean status;

    private String msg;

    private Long creator;

    @TableField(fill = FieldFill.INSERT)
    private Long createTime;

    private static final long serialVersionUID = 3066743231322638960L;
}