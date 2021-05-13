package cn.tucci.management.model.body;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author tucci.lee
 */
@Data
public class RoleAddBody {

    @NotNull(message = "角色名称不能为空")
    @Length(min = 1, max = 10, message = "非法的角色名称")
    private String name;

    @Length(max = 50, message = "非法的角色字符")
    private String roleChar;

    @Length(max = 200, message = "非法的备注")
    private String remarks;

    @NotEmpty(message = "未选择资源")
    private List<Long> resIds;
}
