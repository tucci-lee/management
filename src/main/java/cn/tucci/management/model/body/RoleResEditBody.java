package cn.tucci.management.model.body;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author tucci.lee
 */
@Data
public class RoleResEditBody {

    @NotNull(message = "非法请求")
    private Long id;

    @NotEmpty(message = "未选择资源")
    private List<Long> resIds;
}
