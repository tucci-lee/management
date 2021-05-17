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
@TableName("log_login")
public class LogLogin implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String account;

    private String os;

    private String browser;

    private String ip;

    @TableField(fill = FieldFill.INSERT)
    private Long createTime;

    private Boolean status;

    private String msg;

    private static final long serialVersionUID = 7185477559570215299L;
}