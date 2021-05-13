package cn.tucci.management.model.body;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 *
 * @author tucci.lee
 */
@Data
public class RoleEditBody {

    @NotNull(message = "非法请求")
    private Long id;

    @NotNull(message = "角色名称不能为空")
    @Length(min = 1, max = 10, message = "非法的角色名称")
    private String name;

    @Length(max = 50, message = "非法的角色字符")
    private String roleChar;

    @Length(max = 200, message = "非法的备注")
    private String remarks;
}
