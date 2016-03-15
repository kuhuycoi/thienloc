package com.resources.controller;

import com.resources.function.CustomFunction;
import com.resources.utils.CaptchaUtil;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/Captcha")
public class CaptchaController {

    public static final String FILE_TYPE = "jpeg";
    
    @RequestMapping(value = "/Trian/{random}", method = RequestMethod.GET)
    public void getTrianCaptcha(@PathVariable(value = "random") String random, HttpServletRequest request, HttpServletResponse response) {
        setCaptcha(request, response, "TRIAN");
    }
    
    @RequestMapping(value = "/CustomerLogin/{random}", method = RequestMethod.GET)
    public void getCustomerLoginCaptcha(@PathVariable(value = "random") String random, HttpServletRequest request, HttpServletResponse response) {
        setCaptcha(request, response, "CUSTOMER_LOGIN");
    }
    
    @RequestMapping(value = "/AdminLogin/{random}", method = RequestMethod.GET)
    public void getAdminLoginCaptcha(@PathVariable(value = "random") String random, HttpServletRequest request, HttpServletResponse response) {
        setCaptcha(request, response, "ADMIN_LOGIN");
    }

    private void setCaptcha(HttpServletRequest request, HttpServletResponse response, String captchaName) {
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Max-Age", 0);

        String captchaStr = "";

        //captchaStr=Util.generateCaptchaTextMethod();
        captchaStr = CaptchaUtil.generateCaptchaTextMethod2(6);

        try {

            int width = 100;
            int height = 40;

            Color bg = new Color(0, 255, 255);
            Color fg = new Color(0, 100, 0);

            Font font = new Font("Arial", Font.BOLD, 20);
            BufferedImage cpimg = new BufferedImage(width, height, BufferedImage.OPAQUE);
            Graphics g = cpimg.createGraphics();

            g.setFont(font);
            g.setColor(bg);
            g.fillRect(0, 0, width, height);
            g.setColor(fg);
            g.drawString(captchaStr, 10, 25);

            HttpSession session = request.getSession(true);
            HashMap captchaList = (HashMap) session.getAttribute("CAPTCHA");
            if (captchaList == null) {
                captchaList = new HashMap();
            }
            captchaList.put(captchaName, CustomFunction.md5(captchaStr));
            session.setAttribute("CAPTCHA", captchaList);

            OutputStream outputStream = response.getOutputStream();

            ImageIO.write(cpimg, FILE_TYPE, outputStream);
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
