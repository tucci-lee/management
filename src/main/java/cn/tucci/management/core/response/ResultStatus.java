package cn.tucci.management.core.response;

/**
 * @author tucci.lee
 */
public enum ResultStatus {
    /** 处理成功 */
    OK(0, "OK"),
    /** 服务器错误 */
    INTERNAL_SERVER_ERROR(-1, "服务器错误"),

    /** 1xxxx 客户端请求错误 */
    METHOD_NOT_ALLOWED(10001, "请求方法不支持"),
    UNSUPPORTED_MEDIA_TYPE(10002, "请求媒体类型不支持"),
    PARAMETER_TYPE_ERROR(10004, "参数类型错误"),
    JSON_PARSE_ERROR(10005, "JSON解析错误"),
    NOT_FOUND(10006, "not found"),
    FREQUENT_REQUESTS(10007, "请求频繁"),

    /** 2xxxx 业务异常 */
    BUSINESS_EXCEPTION(20000, "业务异常"),

    /** 201xx 用户错误 */
    USER_NOT_EXIST(20101, "用户不存在"),
    USER_EXISTS(20102, "用户已存在"),
    ACCOUNT_LOCKED(20103, "账号锁定"),
    INCORRECT_ACCOUNT_OR_PASSWORD(20104, "账号或密码错误"),
    PASSWORD_ERROR(20105, "密码错误"),
    /** 202xx 参数错误 */
    VERIFICATION_CODE_ERROR(20201, "验证码错误"),
    PHONE_VERIFICATION_CODE_ERROR(20202, "手机验证码错误"),
    EMAIL_VERIFICATION_CODE_ERROR(20203, "邮箱验证码错误"),
    PHONE_EXISTS(20204, "手机号已存在"),

    /** 3xxxx 授权认证错误 */
    UNAUTHENTICATED(30001, "未认证"),
    UNAUTHORIZED(30002, "未授权");


    private int code;
    private String msg;

    ResultStatus(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int code() {
        return this.code;
    }

    public String msg() {
        return this.msg;
    }

}
