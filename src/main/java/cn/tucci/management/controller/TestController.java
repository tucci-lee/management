package cn.tucci.management.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tucci.lee
 */
@RestController
public class TestController {

    @RequiresPermissions("a")
    @GetMapping("a")
    public String a(){
        return "a";
    }
}
