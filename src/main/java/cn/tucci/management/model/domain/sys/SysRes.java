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
@TableName("sys_res")
public class SysRes implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String name;

    private Integer type;

    private String url;

    private Long pid;

    @TableField(select = false)
    private String level;

    private String resChar;

    private String icon;

    private Integer seq;

    private Long creator;

    @TableField(fill = FieldFill.INSERT)
    private Long createTime;

    private Long updater;

    @TableField(fill = FieldFill.UPDATE)
    private Long updatedTime;

    @TableField(select = false)
    private Boolean isDeleted;

    private static final long serialVersionUID = -557629705704545782L;

    public interface Type{
        int MENU = 1;
        int AUTHORITY = 2;
    }
}