package cn.tucci.management.model.body;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author tucci.lee
 */
@Data
public class DeptAddBody {

    @NotNull(message = "非法请求")
    private Long pid;

    @NotNull(message = "部门名称不能为空")
    @Length(min = 1, max = 10, message = "非法的部门名称")
    private String name;

    @NotNull(message = "负责人不能为空")
    @Length(min = 1, max = 10, message = "非法的负责人名称")
    private String manager;

    @NotNull(message = "负责人手机不能为空")
    @Pattern(regexp = "^[1][3-9][0-9]{9}$", message = "非法的手机号")
    private String managerPhone;

    @Range(max = 99, message = "非法的部门排序")
    private Integer seq;
}
