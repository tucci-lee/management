package cn.tucci.management.model.domain.log;

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
@TableName("log_operation")
public class LogOperation implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long uid;

    private String account;

    private String ip;

    private String url;

    private String method;

    private String params;

    private String result;

    private String description;

    private String errMsg;

    @TableField(fill = FieldFill.INSERT)
    private Long createTime;

    private Boolean status;

    private static final long serialVersionUID = 8995622800669237014L;
}