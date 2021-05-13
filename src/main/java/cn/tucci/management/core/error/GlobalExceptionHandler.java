package cn.tucci.management.core.error;

import cn.tucci.management.core.exception.BusinessException;
import cn.tucci.management.core.response.Result;
import cn.tucci.management.core.response.ResultStatus;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;

/**
 * @author tucci.lee
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 自定义异常
     *
     * @param e BusinessException
     * @return Result
     */
    @ExceptionHandler(BusinessException.class)
    public Result businessExceptionHandler(BusinessException e) {
        return Result.fail(e.getCode(), e.getMessage());
    }

    /**
     * 参数校验异常
     *
     * @param e MethodArgumentNotValidException
     * @return Result
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getFieldError().getDefaultMessage();
        return Result.fail(msg);
    }

    /**
     * 参数校验异常
     *
     * @param e ConstraintViolationException
     * @return Result
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result constraintViolationExceptionHandler(ConstraintViolationException e) {
        String msg = e.getMessage();
        return Result.fail(msg);
    }

    /**
     * 参数校验异常
     *
     * @param e BindException
     * @return Result
     */
    @ExceptionHandler(BindException.class)
    public Result bindExceptionHandler(BindException e) {
        String msg = e.getBindingResult().getFieldError().getDefaultMessage();
        return Result.fail(msg);
    }

    /**
     * shiro未授权
     *
     * @param e UnauthorizedException
     * @return Result
     */
    @ExceptionHandler(UnauthorizedException.class)
    public Result unauthorizedExceptionHandler(UnauthorizedException e) {
        return Result.fail(ResultStatus.UNAUTHORIZED);
    }

    /**
     * 全局异常捕获
     *
     * @param e Throwable
     * @return Result
     */
    @ExceptionHandler(Throwable.class)
    public Result globalHandler(Throwable e) {
        Result result;
        if (e instanceof HttpRequestMethodNotSupportedException) {
            result = Result.fail(ResultStatus.METHOD_NOT_ALLOWED);
        } else if (e instanceof HttpMessageNotReadableException) {
            result = Result.fail(ResultStatus.JSON_PARSE_ERROR);
        } else if (e instanceof HttpMediaTypeNotSupportedException) {
            result = Result.fail(ResultStatus.UNSUPPORTED_MEDIA_TYPE);
        } else if (e instanceof MethodArgumentTypeMismatchException) {
            result = Result.fail(ResultStatus.PARAMETER_TYPE_ERROR);
        } else {
            result = Result.fail(ResultStatus.INTERNAL_SERVER_ERROR);
            log.error(e.getMessage(), e);
        }

        return result;
    }
}
