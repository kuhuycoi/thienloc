package com.resources.interceptor;

import com.resources.entity.Admin;
import com.resources.entity.Module;
import com.resources.entity.ModuleInRole;
import com.resources.facade.AdminFacade;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class AdminAuthenticationInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        String rootContext = request.getServletContext().getContextPath();
        Admin aID = new AdminFacade().findAdminById((Integer) request.getSession().getAttribute("ADMIN_ID"));
        String uri = request.getRequestURI();
        if (aID == null) {
            response.getWriter().write("<script>window.location.href='" + rootContext + "/Admin/Login'</script>");
            return false;
        } else if (uri.endsWith(".jsp") || uri.endsWith(".html") || uri.endsWith("/Admin") || uri.endsWith("/admin")) {
            return true;
        } else {
            String[] strs = uri.split("/");
            String includeUri = "";
            String includeUri1 = "";
            if (strs.length >= 2) {
                includeUri = "/" + strs[2];
            }
            for (ModuleInRole mIR : aID.getRoleAdmID().getModuleInRoles()) {
                if (includeUri.equalsIgnoreCase(mIR.getModuleID().getController())) {
                    if (strs.length >= 3) {
                        includeUri1 = "/" + strs[3];
                        for (Module module : mIR.getModuleID().getModules()) {
                            if (includeUri1.equalsIgnoreCase(module.getController())) {
                                return true;
                            }
                        }
                        return false;
                    }
                    return true;
                }
            }
            return false;
        }
    }
}
