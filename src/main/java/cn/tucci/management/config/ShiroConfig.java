package cn.tucci.management.config;

import cn.tucci.management.shiro.filter.UserFilter;
import cn.tucci.management.shiro.realm.AccountRealm;
import cn.tucci.management.shiro.session.CustomSessionFactory;
import cn.tucci.management.shiro.session.RedisSessionDAO;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tucci.lee
 */
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        // 过滤链
        Map<String, String> filterChainMap = new LinkedHashMap<>();
        filterChainMap.put("/", "anon");
        filterChainMap.put("/error/**", "anon");
        filterChainMap.put("/kaptcha/**", "anon");
        filterChainMap.put("/login", "anon");
        filterChainMap.put("/druid/**", "perms[monitor:druid:list]");
        filterChainMap.put("/**", "user");

        // 自定义filter
        UserFilter userFilter = new UserFilter();
        // session配置
        userFilter.setSessionDAO(redisSessionDAO());

        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("user", userFilter);

        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);
        bean.setFilterChainDefinitionMap(filterChainMap);
        bean.setFilters(filters);

        return bean;
    }

    @Bean
    public SecurityManager securityManager(AccountRealm accountRealm) {
        List<Realm> realms = new ArrayList<>();
        realms.add(accountRealm);

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealms(realms);
        securityManager.setRememberMeManager(rememberMeManager());

        // session配置
        DefaultSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        sessionManager.setSessionFactory(new CustomSessionFactory());
        securityManager.setSessionManager(sessionManager);

        return securityManager;
    }

    /**
     * cookie管理对象;记住我功能
     *
     * @return RememberMeManager
     */
    public RememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        /*
         * rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
         *
         * KeyGenerator keygen = KeyGenerator.getInstance("AES");
         * SecretKey deskey = keygen.generateKey();
         * System.out.println(Base64.encodeToString(deskey.getEncoded()));
         */
        cookieRememberMeManager.setCipherKey(Base64.decode("66v1O8keKNV3TTcGPK1wzg=="));
        return cookieRememberMeManager;
    }

    @Resource
    private RedisTemplate<String, Session> redisTemplate;
    /** session 配置 */
    @Bean
    public RedisSessionDAO redisSessionDAO(){
        return new RedisSessionDAO(redisTemplate);
    }

    /**
     * 开启shiro注解，依赖spring aop
     *
     * @param securityManager securityManager
     * @return AuthorizationAttributeSourceAdvisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
