package cn.tucci.management.model.query;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author tucci.lee
 */
@Data
public class TaskScheduleQuery {

    private String name;

    private Integer type;

    private Integer status;

    private Set<Long> ids;

    @NotNull(message = "分页条件不能为空")
    private Long pageNum;

    @NotNull(message = "非法请求")
    private Boolean asc;
    @NotNull(message = "非法请求")
    private String column;
}
