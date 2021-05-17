package cn.tucci.management.model.body;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author tucci.lee
 */
@Data
public class TaskScheduleEditStatusBody {

    @NotNull(message = "非法请求")
    private Long id;

    @NotNull(message = "任务状态不能为空")
    private Boolean status;
}
