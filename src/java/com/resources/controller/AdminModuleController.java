package com.resources.controller;

import com.resources.entity.Module;
import com.resources.entity.ModuleInRole;
import com.resources.entity.RoleAdmin;
import com.resources.facade.ModuleFacade;
import com.resources.facade.RoleAdminFacade;
import com.resources.pagination.admin.DefaultAdminPagination;
import com.resources.pagination.admin.ModulePagination;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/Admin/Module")
public class AdminModuleController {
    @RequestMapping(value = "/Sidebar", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getSidebar(ModelMap mm) {
        return new ModelAndView(DefaultAdminPagination.CONTAINER_FOLDER + new ModulePagination("/module_sidebar", "/Sidebar").getViewName());
    }

    @RequestMapping(value = "/Insert", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView insert(@RequestParam(value = "module") Module module, ModelMap mm) {
        return new ModelAndView("includes/admin/message_content");
    }
    
    //ListModule    
    @RequestMapping(value = "/ListModule", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDefaultListModuleView(ModelMap mm, HttpSession session) {
        ModulePagination modulePagination = (ModulePagination) session.getAttribute("LIST_MODULE_PAGINATION");
        if (modulePagination == null) {
            modulePagination = new ModulePagination("/module_list_module", "/ListModule");
        }
        modulePagination.setViewTitle("Danh sách module");
        session.setAttribute("LIST_MODULE_PAGINATION", modulePagination);
        mm.put("MODULES", new ModuleFacade().findAllModuleByLevel(1));
        return new ModelAndView(DefaultAdminPagination.CONTAINER_FOLDER + modulePagination.getViewName());
    }
}
