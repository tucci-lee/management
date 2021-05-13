package cn.tucci.management.shiro.util;

import cn.tucci.management.model.domain.sys.SysUser;
import org.apache.shiro.SecurityUtils;

/**
 *
 */
public class ShiroUtil {

    public static <T> T getPrincipal(Class<T> clazz) {
        return (T) SecurityUtils.getSubject().getPrincipal();
    }

    public static SysUser getUser(){
        return getPrincipal(SysUser.class);
    }

    public static Long getUid(){
        return getUser().getUid();
    }

}
