package cn.tucci.management.controller.sys;

import cn.tucci.management.core.response.PageResult;
import cn.tucci.management.core.response.Result;
import cn.tucci.management.core.util.Assert;
import cn.tucci.management.core.util.WebUtil;
import cn.tucci.management.model.dto.SessionDTO;
import cn.tucci.management.shiro.session.CustomSession;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 */
@RestController
@RequestMapping("monitor/session")
@Validated
public class SessionMonitorController {

    private final SessionDAO sessionDAO;

    @Autowired
    public SessionMonitorController(SessionDAO sessionDAO) {
        this.sessionDAO = sessionDAO;
    }

    /**
     * 获取所有的session,如果登陆会有用户信息。
     * 将session copy到SessionDTO中，避免session中setAttribute的数据泄漏
     *
     * @param pageNum pageNum
     * @return Result
     */
    @RequiresPermissions(value = {"monitor:session:list"})
    @GetMapping
    public Result sessions(@NotNull(message = "分页条件不能为空") Integer pageNum) {
        Collection<Session> sessionCollection = sessionDAO.getActiveSessions();
        if (sessionCollection == null || sessionCollection.isEmpty()) {
            return Result.ok();
        }
        Session[] sessionArray = new Session[sessionCollection.size()];
        List<Session> sessions = Arrays.asList(sessionCollection.toArray(sessionArray));

        int formIndex = (pageNum - 1) * 10;
        int toIndex = pageNum * 10;
        // 如果开始数据下标大于sessions的长度
        if (formIndex > sessions.size()) {
            return PageResult.ok(Collections.emptyList(), (long) sessions.size());
        }
        // 如果结束数据下标大于sessions的长度则截取到sessions的最后一条
        List<Session> subSessions;
        if (toIndex > sessions.size()) {
            subSessions = sessions.subList(formIndex, sessions.size());
        } else {
            subSessions = sessions.subList(formIndex, toIndex);
        }

        List<SessionDTO> result = new ArrayList<>();
        subSessions.forEach(session -> {
            SessionDTO dto = new SessionDTO();
            BeanUtils.copyProperties(session, dto);
            result.add(dto);
        });
        return PageResult.ok(result, (long) sessions.size());
    }

    /**
     * 删除session，如果用户登陆过会变成退出状态，如果用户记住我则重新获取一个新的session
     * 无法删除当前正在操作的session
     *
     * @param sessionId sessionId
     * @return Result
     */
    @RequiresPermissions(value = {"monitor:session:delete"})
    @DeleteMapping("{sessionId}")
    public Result delete(@PathVariable String sessionId) {
        String id = WebUtil.getSession().getId();
        Assert.isTrue(!id.equals(sessionId), "无法删除当前session");
        CustomSession session = new CustomSession();
        session.setId(sessionId);
        sessionDAO.delete(session);
        return Result.ok();
    }
}
