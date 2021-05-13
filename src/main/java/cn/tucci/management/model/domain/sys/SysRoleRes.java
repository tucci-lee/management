package cn.tucci.management.model.domain.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@TableName("sys_role_res")
public class SysRoleRes implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long roleId;

    private Long resId;

    private static final long serialVersionUID = 6141853043489332268L;
}