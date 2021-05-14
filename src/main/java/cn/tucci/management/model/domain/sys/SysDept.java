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
@TableName("sys_dept")
public class SysDept implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String name;

    private Long pid;

    @TableField(select = false)
    private String level;

    private Integer seq;

    private String manager;

    private String managerPhone;

    private Long creator;

    @TableField(fill = FieldFill.INSERT)
    private Long createTime;

    private Long updater;

    @TableField(fill = FieldFill.UPDATE)
    private Long updatedTime;

    @TableField(select = false)
    private Boolean isDeleted;

    private static final long serialVersionUID = -4179689260523850269L;
}