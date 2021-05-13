package cn.tucci.management.shiro.filter;

import cn.tucci.management.core.response.Result;
import cn.tucci.management.core.response.ResultStatus;
import cn.tucci.management.shiro.session.CustomSession;
import cn.tucci.management.shiro.util.ShiroUtil;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户登陆filter
 *
 * @author tucci.lee
 */
public class UserFilter extends AccessControlFilter {

    private SessionDAO sessionDAO;

    /**
     * 根据subject判断用户是否登陆
     *
     * @param request  request
     * @param response response
     * @param o        object
     * @return isAccessAllowed
     * @throws Exception Exception
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object o) throws Exception {
        Subject subject = this.getSubject(request, response);
        if (subject.getPrincipal() != null) {
            this.otherExecute(subject);
            return true;
        }
        return false;
    }

    /**
     * 如果用户未登陆的处理
     *
     * @param request  request
     * @param response response
     * @return onAccessDenied
     * @throws Exception Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        String contentType = request.getContentType();
        HttpServletResponse res = (HttpServletResponse) response;
        res.setContentType("application/json;charset=UTF-8");
        String result = JSON.toJSONString(Result.fail(ResultStatus.UNAUTHENTICATED));
        res.getWriter().print(result);
        return false;
    }

    /**
     * 用户其他操作，
     * 暂时只是更新用户登陆了，设置用户信息到session中
     *
     * @param subject subject
     */
    protected void otherExecute(Subject subject) {
        if (sessionDAO != null) {
            // 更新shiro session中的信息
            CustomSession session = (CustomSession) sessionDAO.readSession(subject.getSession().getId());
            if(session.getUid() == null) {
                session.setUid(ShiroUtil.getUid());
                session.setAccount(ShiroUtil.getUser().getAccount());
                sessionDAO.update(session);
            }
        }
    }

    public void setSessionDAO(SessionDAO sessionDAO) {
        this.sessionDAO = sessionDAO;
    }
}
