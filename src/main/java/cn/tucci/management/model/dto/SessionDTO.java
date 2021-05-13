package cn.tucci.management.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 */
@Data
public class SessionDTO {

    private Long uid;

    private String account;

    private String ip;

    private String browser;

    private String os;

    private Serializable id;
    private Date startTimestamp;
    private Date stopTimestamp;
    private Date lastAccessTime;
    private long timeout;
    private boolean expired;
    private String host;
}
