package cn.tucci.management.core.util;

import cn.tucci.management.core.exception.BusinessException;
import cn.tucci.management.shiro.util.ShiroUtil;

/**
 * @author tucci.lee
 */
public class RootUtil {

    private static final Long ROOT_UID = 1L;

    /**
     * 管理员只能自己操作自己的信息
     *
     * @param uid
     */
    public static void isRoot(Long uid) {
        Long currentUid = ShiroUtil.getUid();
        if (ROOT_UID.equals(uid) && !currentUid.equals(uid)) {
            throw new BusinessException("无权限修改管理员");
        }
    }

    /**
     * 不能修改的管理员信息
     *
     * @param uid
     */
    public static void notEditRoot(Long uid) {
        if (ROOT_UID.equals(uid)) {
            throw new BusinessException("无法修改管理员");
        }
    }
}
