package cn.tucci.management.shiro.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * session DAO重写
 */
public class RedisSessionDAO extends AbstractSessionDAO {

    protected static final String SESSION_KEY = "session:id:";

    private final RedisTemplate<String, Session> redisTemplate;

    public RedisSessionDAO(RedisTemplate<String, Session> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        redisTemplate.opsForValue().set(SESSION_KEY + sessionId, session, 30, TimeUnit.MINUTES);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable serializable) {
        return redisTemplate.opsForValue().get(SESSION_KEY + serializable);
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        redisTemplate.opsForValue().set(SESSION_KEY + session.getId(), session, 30, TimeUnit.MINUTES);
    }

    @Override
    public void delete(Session session) {
        redisTemplate.delete(SESSION_KEY + session.getId());
    }

    @Override
    public List<Session> getActiveSessions() {
        Set<String> keys = redisTemplate.keys(SESSION_KEY + "*");
        if (keys == null || keys.isEmpty()) {
            return new ArrayList<>();
        }
        return redisTemplate.opsForValue().multiGet(keys);
    }
}
