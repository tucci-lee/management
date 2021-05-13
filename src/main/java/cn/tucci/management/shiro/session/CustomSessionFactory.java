package cn.tucci.management.shiro.session;

import cn.tucci.management.core.util.WebUtil;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.web.session.mgt.WebSessionContext;
import org.springframework.http.HttpHeaders;

/**
 * session创建工厂重写
 */
public class CustomSessionFactory implements SessionFactory {
    @Override
    public Session createSession(SessionContext sessionContext) {
        WebSessionContext webSessionContext = (WebSessionContext) sessionContext;
        String host = webSessionContext.getHost();
        String ip = WebUtil.getIp();
        // 获取浏览器信息（操作系统，浏览器）
        UserAgent userAgent = UserAgent.parseUserAgentString(WebUtil.getHeader(HttpHeaders.USER_AGENT));
        String os = userAgent.getOperatingSystem().getName();
        String browser = userAgent.getBrowser().getName();

        CustomSession session = new CustomSession();
        session.setHost(host);
        session.setBrowser(browser);
        session.setIp(ip);
        session.setOs(os);

        return session;
    }
}
