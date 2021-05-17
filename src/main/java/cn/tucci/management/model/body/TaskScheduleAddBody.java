package cn.tucci.management.model.body;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author tucci.lee
 */
@Data
public class TaskScheduleAddBody {

    @NotNull(message = "任务名称不能为空")
    @Length(max = 20, message = "非法的任务名称")
    private String name;

    @NotNull(message = "任务类名不能为空")
    @Length(max = 100, message = "非法的任务类名")
    private String className;

    @NotNull(message = "cron不能为空")
    @Length(max = 50, message = "非法的cron")
    private String cron;

    @NotNull(message = "任务类型不能为空")
    @Range(min = 1, max = 2, message = "非法的任务类型")
    private Integer type;

    @Length(max = 200, message = "非法的备注")
    private String remarks;
}
