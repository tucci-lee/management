package cn.tucci.management.shiro.session;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.shiro.session.mgt.SimpleSession;

/**
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomSession extends SimpleSession {

    private Long uid;

    private String account;

    private String ip;

    private String browser;

    private String os;
}
