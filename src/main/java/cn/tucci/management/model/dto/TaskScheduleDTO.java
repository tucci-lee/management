package cn.tucci.management.model.dto;

import cn.tucci.management.model.domain.monitor.TaskSchedule;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author tucci.lee
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TaskScheduleDTO extends TaskSchedule {

    private Boolean status;
}
