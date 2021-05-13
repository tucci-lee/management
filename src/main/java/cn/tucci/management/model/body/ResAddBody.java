package cn.tucci.management.model.body;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author tucci.lee
 */
@Data
public class ResAddBody {
    @NotNull(message = "非法请求")
    private Long pid;

    @NotNull(message = "资源名称不能为空")
    @Length(min = 1, max = 10, message = "非法的资源名称")
    private String name;

    @NotNull(message = "资源类型不能为空")
    @Range(min = 1, max = 2, message = "非法的资源类型")
    private Integer type;

    @Length(max = 100, message = "非法的资源url")
    private String url;

    @Length(max = 50, message = "非法的资源字符")
    private String resChar;

    @Length(max = 100, message = "非法的资源图标")
    private String icon;

    @Range(max = 99, message = "非法的资源排序")
    private Integer seq;

}
