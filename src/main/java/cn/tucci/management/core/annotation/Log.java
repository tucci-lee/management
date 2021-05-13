package cn.tucci.management.core.annotation;

import cn.tucci.management.core.aspect.LogAspect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 记录日志
 *
 * @see LogAspect
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /** 日志描述 */
    String value() default "";

    /** 是否记录参数 */
    boolean recordParams() default true;
}
