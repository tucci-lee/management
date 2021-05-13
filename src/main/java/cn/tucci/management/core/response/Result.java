package cn.tucci.management.core.response;

import java.io.Serializable;

/**
 * @author tucci.lee
 */
public class Result implements Serializable {

    private Boolean status;
    private Integer code;
    private String msg;
    private Object data;

    public Result(boolean status, int code, String msg, Object data){
        this.status = status;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result ok(Object data){
        return new Result(Boolean.TRUE, ResultStatus.OK.code(), ResultStatus.OK.msg(), data);
    }

    public static Result ok(){
        return ok(null);
    }


    public static Result fail(Integer code, String msg){
        return new Result(Boolean.FALSE, code, msg, null);
    }

    public static Result fail(ResultStatus resultStatus){
        return fail(resultStatus.code(), resultStatus.msg());
    }

    public static Result fail(String msg){
        return fail(ResultStatus.BUSINESS_EXCEPTION.code(), msg);
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "status=" + status +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
