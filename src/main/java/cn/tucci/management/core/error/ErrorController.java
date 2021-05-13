package cn.tucci.management.core.error;

import cn.tucci.management.core.response.Result;
import cn.tucci.management.core.response.ResultStatus;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 错误页面
 *
 * @author tucci.lee
 */
@Controller
@RequestMapping({"${server.error.path:${error.path:/error}}"})
public class ErrorController extends AbstractErrorController {

    public ErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @RequestMapping(produces = {"text/html"})
    public String errorHtml(){
        return "redirect:/error/404.html";
    }

    @RequestMapping
    @ResponseBody
    public Result error(){
        return Result.fail(ResultStatus.NOT_FOUND);
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
