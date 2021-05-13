/**
 *
 * 重写session存储，为了方便对session监控。使用redis存储也实现了session共享
 * {@link cn.tucci.management.shiro.session.RedisSessionDAO}需要在
 * {@link cn.tucci.management.config.ShiroConfig#securityManager(cn.tucci.management.shiro.realm.AccountRealm)}
 * 方法中配置
 *
 * 如果不需要使用自定义session，在{@link cn.tucci.management.config.ShiroConfig}中删除有关session配置即可
 *
 * @see {@link cn.tucci.management.shiro.filter.UserFilter#otherExecute(org.apache.shiro.subject.Subject)}
 */
package cn.tucci.management.shiro.session;