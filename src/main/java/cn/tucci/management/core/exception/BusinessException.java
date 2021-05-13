package cn.tucci.management.core.exception;


import cn.tucci.management.core.response.ResultStatus;

/**
 * @author tucci.lee
 */
public class BusinessException extends RuntimeException {

    private Integer code;

    public BusinessException() {
        this(ResultStatus.BUSINESS_EXCEPTION);
    }

    public BusinessException(String msg) {
        this(ResultStatus.BUSINESS_EXCEPTION.code(), msg);
    }

    protected BusinessException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public BusinessException(ResultStatus resultStatus) {
        this(resultStatus.code(), resultStatus.msg());
    }

    public BusinessException(Exception e) {
        this(e.getMessage());
    }

    public Integer getCode() {
        return code;
    }

}
