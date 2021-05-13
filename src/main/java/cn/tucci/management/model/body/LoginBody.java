package cn.tucci.management.model.body;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author tucci.lee
 */
@Data
public class LoginBody {

    @NotNull(message = "账号不能为空")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]{3,9}$", message = "非法的账号")
    private String account;

    @NotNull(message = "密码不能为空")
    @Length(min = 6, max = 16, message = "非法的密码")
    private String password;

    @NotNull(message = "验证码不能为空")
    private String kaptcha;

    @NotNull(message = "非法请求")
    private Boolean rememberMe;
}
