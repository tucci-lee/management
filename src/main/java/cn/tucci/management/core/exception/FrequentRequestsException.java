package cn.tucci.management.core.exception;

import cn.tucci.management.core.response.ResultStatus;

/**
 * @author tucci.lee
 */
public class FrequentRequestsException extends BusinessException {

    public FrequentRequestsException() {
        super();
    }

    public FrequentRequestsException(Integer code, String msg) {
        super(code, msg);
    }

    public FrequentRequestsException(String msg) {
        super(msg);
    }

    public FrequentRequestsException(ResultStatus status) {
        super(status);
    }

    public FrequentRequestsException(Exception e) {
        super(e);
    }
}
