package cn.tucci.management.model.body;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author tucci.lee
 */
@Data
public class UserPwdEditBody {
    @NotNull(message = "非法请求")
    private Long uid;

    @NotNull(message = "密码不能为空")
    @Length(min = 6, max = 16, message = "非法的密码")
    private String password;
}
