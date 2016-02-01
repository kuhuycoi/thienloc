package com.resources.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class IndexAuthenticationInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        String rootContext = request.getServletContext().getContextPath();
        String uri = request.getRequestURI().toLowerCase();
        Object sessionObj = request.getSession().getAttribute("CUSTOMER_ID");

        if (sessionObj == null && !uri.endsWith("/dang-nhap") && !uri.endsWith("/dang-ky") && !uri.endsWith("/register") && !uri.endsWith("/login")) {
            response.getWriter().write("<script>window.location.href='" + rootContext + "/dang-nhap'</script>");
            return false;
        }

        if ((sessionObj != null && uri.endsWith("/logout")) || (sessionObj != null && uri.endsWith("/thoat"))) {
            request.getSession(false).invalidate();
            response.getWriter().write("<script>window.location.href='" + rootContext + "/dang-nhap'</script>");
            return false;
        }
        if ((sessionObj != null && uri.endsWith("/dang-nhap")) || (sessionObj != null && uri.endsWith("/dang-ky"))
                || (sessionObj != null && uri.endsWith("/Login")) || (sessionObj != null && uri.endsWith("/Register"))) {
            response.getWriter().write("<script>window.location.href='" + rootContext + "/trang-chu'</script>");
        }
        return true;
    }
}
