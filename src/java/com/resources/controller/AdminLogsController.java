package com.resources.controller;

import com.resources.facade.AdminLogsFacade;
import com.resources.pagination.admin.DefaultAdminPagination;
import com.resources.pagination.admin.AdminLogsPagination;
import com.resources.utils.StringUtils;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/Admin/AdminLogs")
public class AdminLogsController {
    
    //Logs    
    @RequestMapping(value = "/Logs", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDefaultCustomerRankCustomerView(ModelMap mm, HttpSession session) {
        AdminLogsPagination adminLogsPagination = (AdminLogsPagination) session.getAttribute("ADMIN_LOGS_LOGS_PAGINATION");
        if(adminLogsPagination==null){
            adminLogsPagination = new AdminLogsPagination("/admin_logs_logs", "/Logs");
        }
        session.setAttribute("ADMIN_LOGS_LOGS_PAGINATION", adminLogsPagination);
        return new ModelAndView(DefaultAdminPagination.CONTAINER_FOLDER + adminLogsPagination.getViewName());
    }

    @RequestMapping(value = "/Logs/DisplayPerPage/{displayPerPage}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeDisplayPerPageForCustomerRankCustomerView(@PathVariable("displayPerPage") int displayPerPage, ModelMap mm, HttpSession session) {
        AdminLogsPagination adminLogsPagination = (AdminLogsPagination) session.getAttribute("ADMIN_LOGS_LOGS_PAGINATION");
        if (adminLogsPagination != null) {
            adminLogsPagination.setDisplayPerPage(displayPerPage);
        }
        return customerRankCustomerView(adminLogsPagination, session);
    }

    @RequestMapping(value = "/Logs/OrderData/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView orderByCustomerRankCustomerView(@PathVariable("orderBy") String orderBy, ModelMap mm, HttpSession session) {
        AdminLogsPagination adminLogsPagination = (AdminLogsPagination) session.getAttribute("ADMIN_LOGS_LOGS_PAGINATION");
        if (adminLogsPagination != null) {
            if (adminLogsPagination.getOrderColmn().equals(orderBy)) {
                adminLogsPagination.setAsc(!adminLogsPagination.isAsc());
            }
            adminLogsPagination.setOrderColmn(orderBy);
        }
        return customerRankCustomerView(adminLogsPagination, session);
    }

    @RequestMapping(value = "/Logs/GoTo/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoCustomerRankCustomerView(@PathVariable("page") int page, ModelMap mm, HttpSession session) {
        AdminLogsPagination adminLogsPagination = (AdminLogsPagination) session.getAttribute("ADMIN_LOGS_LOGS_PAGINATION");
        if (adminLogsPagination != null) {
            adminLogsPagination.setCurrentPage(page);
        }
        return customerRankCustomerView(adminLogsPagination, session);
    }

    @RequestMapping(value = "/Logs/Search", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public ModelAndView searchDistributorView(@RequestBody Map map, HttpSession session) {
        String searchString = (String) map.get("searchString");
        if (StringUtils.isEmpty(searchString)) {
            return customerRankCustomerView(null, session);
        }
        List<String> keywords = (List) map.get("keywords");
        AdminLogsPagination adminLogsPagination = new AdminLogsPagination("/admin_logs_logs", "/Logs");
        adminLogsPagination.setSearchString(searchString);
        adminLogsPagination.setKeywords(keywords);
        return customerRankCustomerView(adminLogsPagination, session);
    }

    private ModelAndView customerRankCustomerView(AdminLogsPagination adminLogsPagination, HttpSession session) {
        if (adminLogsPagination == null) {
            adminLogsPagination = new AdminLogsPagination("/admin_logs_logs", "/Logs");
        }
        new AdminLogsFacade().pageData(adminLogsPagination);
        session.setAttribute("ADMIN_LOGS_LOGS_PAGINATION", adminLogsPagination);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + adminLogsPagination.getViewName());
    }
}
