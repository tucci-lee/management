package cn.tucci.management.model.query;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 *
 */
@Data
public class UserQuery {

    private String account;

    private String phone;

    private Boolean isLock;

    private Long deptId;

    @NotNull(message = "分页条件不能为空")
    private Long pageNum;

    @NotNull(message = "非法请求")
    private Boolean asc;
    @NotNull(message = "非法请求")
    private String column;
}
