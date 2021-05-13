package cn.tucci.management.model.body;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author tucci.lee
 */
@Data
public class UserRoleEditBody {

    @NotNull(message = "非法请求")
    private Long uid;

    @NotEmpty(message = "未选择角色")
    private List<Long> roleIds;
}
