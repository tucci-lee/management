package cn.tucci.management.model.domain.sys;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@TableName("sys_role")
public class SysRole implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String roleChar;

    private String name;

    private String remarks;

    private Long creator;

    @TableField(fill = FieldFill.INSERT)
    private Long createTime;

    private Long updater;

    @TableField(fill = FieldFill.UPDATE)
    private Long updateTime;

    @TableField(select = false)
    private Boolean isDeleted;

    private static final long serialVersionUID = -8720782996386219648L;
}