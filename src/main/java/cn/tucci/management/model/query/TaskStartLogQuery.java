package cn.tucci.management.model.query;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author tucci.lee
 */
@Data
public class TaskStartLogQuery {

    @NotNull(message = "非法请求")
    private Long taskId;

    @NotNull(message = "分页条件不能为空")
    private Integer pageNum;

    @NotNull(message = "非法请求")
    private Boolean asc;
    @NotNull(message = "非法请求")
    private String column;
}
