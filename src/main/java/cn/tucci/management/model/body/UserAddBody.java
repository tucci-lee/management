package cn.tucci.management.model.body;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * @author tucci.lee
 */
@Data
public class UserAddBody {

    @NotNull(message = "账号不能为空")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]{3,9}$", message = "非法的账号")
    private String account;

    @NotNull(message = "密码不能为空")
    @Length(min = 6, max = 16, message = "非法的密码")
    private String password;

    @NotNull(message = "手机号不能为空")
    @Pattern(regexp = "^[1][3-9][0-9]{9}$", message = "非法的手机号")
    private String phone;

    @Email(message = "非法的邮箱")
    private String email;

    @Length(max = 200, message = "非法的备注")
    private String remarks;

    private Long deptId;

    @NotEmpty(message = "未选择角色")
    private List<Long> roleIds;
}
