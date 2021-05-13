package cn.tucci.management.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 限制访问，从第一次访问接口时间到cycle周期时间内，最多访问frequency次。
 * 如果在周期时间内超出了次数，则expireTime时间后才可以正常访问.
 * 默认使用spel表达式获取的参数为key，如果未传则使用ip地址限制
 * @see cn.tucci.management.core.aspect.LimitAspect
 *
 * @author tucci.lee
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Limit {

    /**
     * spel表达式获取限制访问接口的key
     * @return String
     */
    String value() default "";

    /**
     * 从第一次访问接口的时间到cycle周期时间内，无法超过frequency次
     *
     * @return int
     */
    int frequency() default 10;

    /**
     * 周期时间,单位s：
     * 默认周期时间为一分钟
     *
     * @return int
     */
    int cycle() default 60;

    /**
     * 返回的错误信息
     *
     * @return String
     */
    String message() default "请求过于频繁";

    /**
     * 到期时间,单位s：
     * 如果在cycle周期时间内超过frequency次，则默认1分钟内无法继续访问
     *
     * @return int
     */
    int expireTime() default 60;
}
