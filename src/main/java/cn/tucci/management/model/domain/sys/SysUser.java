package cn.tucci.management.model.domain.sys;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@TableName("sys_user")
public class SysUser implements Serializable {
    @TableId
    private Long uid;

    private String account;

    @TableField(select = false)
    private String password;

    private String phone;

    private String email;

    private Boolean isLock;

    private String nickname;

    private String remarks;

    private Long deptId;

    @TableField(fill = FieldFill.INSERT)
    private Long creator;

    @TableField(fill = FieldFill.INSERT)
    private Long createTime;

    @TableField(fill = FieldFill.UPDATE)
    private Long updater;

    @TableField(fill = FieldFill.UPDATE)
    private Long updatedTime;

    private Boolean isDeleted;

    private static final long serialVersionUID = -7101102487327101314L;
}