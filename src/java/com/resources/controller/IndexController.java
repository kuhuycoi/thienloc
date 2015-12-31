package com.resources.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/")
public class IndexController {

    @RequestMapping(value = {"/trang-chu","/Home"}, method = RequestMethod.GET)
    public ModelAndView getView(ModelMap mm, HttpSession session) {
        return new ModelAndView("index");
    }

    @RequestMapping(value = {"/Login","/dang-nhap"}, method = RequestMethod.GET)
    public ModelAndView getLoginView(ModelMap mm, HttpSession session) {
        return new ModelAndView("index_login");
    }

    @RequestMapping(value = {"/Register","/dang-ky"}, method = RequestMethod.GET)
    public ModelAndView getRegisterView(ModelMap mm, HttpSession session) {
        return new ModelAndView("index_register");
    }

    @RequestMapping(value = {"/Logout","/thoat"}, method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request) {
        request.getSession(false).invalidate();
        return new ModelAndView("login");
    }
}
