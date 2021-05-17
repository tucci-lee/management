package cn.tucci.management.model.query;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 *
 */
@Data
public class LogLoginQuery {

    private String account;

    private String ip;

    private Boolean status;

    private Long beginTime;

    private Long endTime;

    @NotNull(message = "分页条件不能为空")
    private Long pageNum;

    @NotNull(message = "非法请求")
    private Boolean asc;
    @NotNull(message = "非法请求")
    private String column;
}
