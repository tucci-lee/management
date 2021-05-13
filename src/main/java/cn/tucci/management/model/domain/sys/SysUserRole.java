package cn.tucci.management.model.domain.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@TableName("sys_user_role")
public class SysUserRole implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long uid;

    private Long roleId;

    private static final long serialVersionUID = -3702432116752784027L;
}