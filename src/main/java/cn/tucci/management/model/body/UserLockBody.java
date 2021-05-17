package cn.tucci.management.model.body;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author tucci.lee
 */
@Data
public class UserLockBody {

    @NotNull(message = "非法请求")
    private Long uid;

    @NotNull(message = "非法请求")
    private Boolean isLock;
}
