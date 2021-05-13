package cn.tucci.management.controller;

import cn.tucci.management.core.constant.CacheConstant;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author tucci.lee
 */
@Controller
@RequestMapping("kaptcha")
public class KaptchaController {

    private final DefaultKaptcha defaultKaptcha;

    @Autowired
    public KaptchaController(DefaultKaptcha defaultKaptcha) {
        this.defaultKaptcha = defaultKaptcha;
    }

    @GetMapping
    public void img(HttpSession session, HttpServletResponse response) throws IOException {
        // 获取验证码
        String code = defaultKaptcha.createText();
        // 设置session，验证码和获取验证码的时间
        session.setAttribute(CacheConstant.KAPTCHA_IMG_CODE, code);
        session.setAttribute(CacheConstant.KAPTCHA_IMG_TIME, System.currentTimeMillis());
        // 禁止缓存
        response.setDateHeader("Expires", 0L);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        // 生成图片验证码返回
        BufferedImage image = defaultKaptcha.createImage(code);
        ImageIO.write(image, "jpeg", response.getOutputStream());
    }
}
