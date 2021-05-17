package cn.tucci.management.model.dto;

import cn.tucci.management.model.domain.task.TaskStartLog;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author tucci.lee
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TaskStartLogDTO extends TaskStartLog {

    private String account;
}
