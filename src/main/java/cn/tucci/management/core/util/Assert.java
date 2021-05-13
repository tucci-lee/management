package cn.tucci.management.core.util;


import cn.tucci.management.core.exception.BusinessException;
import cn.tucci.management.core.response.ResultStatus;

/**
 * @author tucci.lee
 */
public final class Assert {

    private Assert(){}

    public static void isNull(Object obj, String msg) {
        if (obj != null) {
            throw new BusinessException(msg);
        }
    }

    public static void isNull(Object obj, ResultStatus resultStatus) {
        if (obj != null) {
            throw new BusinessException(resultStatus);
        }
    }

    public static void notNull(Object obj, String msg) {
        if (obj == null) {
            throw new BusinessException(msg);
        }
    }

    public static void notNull(Object obj, ResultStatus resultStatus) {
        if (obj == null) {
            throw new BusinessException(resultStatus);
        }
    }

    public static void isTrue(boolean expression, String msg) {
        if (!expression) {
            throw new BusinessException(msg);
        }
    }

    public static void isTrue(boolean expression, ResultStatus resultStatus){
        if(!expression){
            throw new BusinessException(resultStatus);
        }
    }
}
