package cn.tucci.management.model.body;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author tucci.lee
 */
@Data
public class UserEditBody {

    @NotNull(message = "非法请求")
    private Long uid;

    @NotNull(message = "手机号不能为空")
    @Pattern(regexp = "^[1][3-9][0-9]{9}$", message = "非法的手机号")
    private String phone;

    @Email(message = "非法的邮箱")
    private String email;

    @Length(max = 200, message = "非法的备注")
    private String remarks;

    private Long deptId;
}
