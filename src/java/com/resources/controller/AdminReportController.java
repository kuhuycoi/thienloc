package com.resources.controller;

import com.resources.facade.HistoryAwardFacade;
import com.resources.bean.ExcelFile;
import com.resources.entity.Admin;
import com.resources.facade.AdminFacade;
import com.resources.pagination.admin.DefaultAdminPagination;
import com.resources.pagination.admin.ReportPagination;
import com.resources.utils.StringUtils;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/Admin/Report")
public class AdminReportController {

    @RequestMapping(value = "/ComissionDistributor", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDefaultComissionDistributorView(HttpSession session) {
        ReportPagination reportPagination = (ReportPagination) session.getAttribute("COMISSION_DISTRIBUTOR_PAGINATION");
        if (reportPagination == null) {
            reportPagination = new ReportPagination("Thống kê hoa hồng nhà phân phối", "cusId", true, "/ComissionDistributor", "/report_commission_distributor");
            Admin admin = new AdminFacade().findAdminById((Integer) session.getAttribute("ADMIN_ID"));
            if (admin.getRoleAdmID().getId() == 2 && admin.getProvincialAgencyID().getId() != 0) {
                reportPagination.setAgencyId(admin.getProvincialAgencyID().getId());
            }
        }
        session.setAttribute("COMISSION_DISTRIBUTOR_PAGINATION", reportPagination);
        return new ModelAndView(DefaultAdminPagination.CONTAINER_FOLDER + reportPagination.getViewName());
    }

    @RequestMapping(value = "/ComissionDistributor/DisplayPerPage/{displayPerPage}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeDisplayPerPageForComissionDistributorView(@PathVariable("displayPerPage") int displayPerPage, HttpSession session) {
        ReportPagination reportPagination = (ReportPagination) session.getAttribute("COMISSION_DISTRIBUTOR_PAGINATION");
        if (reportPagination != null) {
            reportPagination.setDisplayPerPage(displayPerPage);
        }
        return comissionDistributorView(reportPagination, session);
    }

    @RequestMapping(value = "/ComissionDistributor/OrderData/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView orderByComissionDistributorView(@PathVariable("orderBy") String orderBy, HttpSession session) {
        ReportPagination reportPagination = (ReportPagination) session.getAttribute("COMISSION_DISTRIBUTOR_PAGINATION");
        if (reportPagination != null) {
            if (reportPagination.getOrderColmn().equals(orderBy)) {
                reportPagination.setAsc(!reportPagination.isAsc());
            }
            reportPagination.setOrderColmn(orderBy);
        }
        return comissionDistributorView(reportPagination, session);
    }

    @RequestMapping(value = "/ComissionDistributor/GoTo/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoComissionDistributorView(@PathVariable("page") int page, HttpSession session) {
        ReportPagination reportPagination = (ReportPagination) session.getAttribute("COMISSION_DISTRIBUTOR_PAGINATION");
        if (reportPagination != null) {
            reportPagination.setCurrentPage(page);
        }
        return comissionDistributorView(reportPagination, session);
    }

    @RequestMapping(value = "/ComissionDistributor/ChangeDay/{type}/{day}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeDayComissionDistributorView(@PathVariable("type") int type, @PathVariable("day") @DateTimeFormat(pattern = "yyyy-MM-dd") Date day, HttpSession session) {
        ReportPagination reportPagination = (ReportPagination) session.getAttribute("COMISSION_DISTRIBUTOR_PAGINATION");
        day = "-1".equals(day) ? null : day;
        if (type == 0) {
            reportPagination.setStartDate(day);
        } else {
            reportPagination.setEndDate(day);
        }
        reportPagination.setCurrentPage(1);
        return comissionDistributorView(reportPagination, session);
    }

    @RequestMapping(value = "/ComissionDistributor/ChangeAgency/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeDayComissionDistributorView(@PathVariable("id") int id, HttpSession session) {
        ReportPagination reportPagination = (ReportPagination) session.getAttribute("COMISSION_DISTRIBUTOR_PAGINATION");
        if (id == -1) {
            reportPagination.setAgencyId(null);
        } else {
            reportPagination.setAgencyId(id);
        }
        Admin admin = new AdminFacade().findAdminById((Integer) session.getAttribute("ADMIN_ID"));
        if (admin.getRoleAdmID().getId() == 2 && admin.getProvincialAgencyID().getId() != 0) {
            reportPagination.setAgencyId(admin.getProvincialAgencyID().getId());
        }
        reportPagination.setCurrentPage(1);
        return comissionDistributorView(reportPagination, session);
    }

    @RequestMapping(value = "/ComissionDistributor/Search", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public ModelAndView searchComissionDistributorView(@RequestBody Map map, HttpSession session) {
        ReportPagination reportPagination = (ReportPagination) session.getAttribute("COMISSION_DISTRIBUTOR_PAGINATION");
        if (reportPagination == null) {
            reportPagination = new ReportPagination("Thống kê hoa hồng nhà phân phối", "cusId", true, "/ComissionDistributor", "/report_commission_distributor");
            Admin admin = new AdminFacade().findAdminById((Integer) session.getAttribute("ADMIN_ID"));
            if (admin.getRoleAdmID().getId() == 2 && admin.getProvincialAgencyID().getId() != 0) {
                reportPagination.setAgencyId(admin.getProvincialAgencyID().getId());
            }
        }
        String searchString = (String) map.get("searchString");
        if (StringUtils.isEmpty(searchString)) {
            return comissionDistributorView(null, session);
        }
        List<String> keywords = (List) map.get("keywords");
        reportPagination.setSearchString(searchString);
        reportPagination.setKeywords(keywords);
        return comissionDistributorView(reportPagination, session);
    }

    private ModelAndView comissionDistributorView(ReportPagination reportPagination, HttpSession session) {
        if (reportPagination == null) {
            reportPagination = new ReportPagination("Thống kê hoa hồng nhà phân phối", "cusId", true, "/ComissionDistributor", "/report_commission_distributor");
            Admin admin = new AdminFacade().findAdminById((Integer) session.getAttribute("ADMIN_ID"));
            if (admin.getRoleAdmID().getId() == 2 && admin.getProvincialAgencyID().getId() != 0) {
                reportPagination.setAgencyId(admin.getProvincialAgencyID().getId());
            }
        }
        new HistoryAwardFacade().pageData(reportPagination);
        session.setAttribute("COMISSION_DISTRIBUTOR_PAGINATION", reportPagination);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + reportPagination.getViewName());
    }

    @RequestMapping(value = "/ComissionDistributor/Export", method = RequestMethod.GET)
    public ModelAndView exportComissionDistributorView(HttpSession session) {
        ReportPagination reportPagination = (ReportPagination) session.getAttribute("COMISSION_DISTRIBUTOR_PAGINATION");
        if (reportPagination == null) {
            reportPagination = new ReportPagination("Thống kê hoa hồng nhà phân phối", "cusId", true, "/ComissionDistributor", "/report_commission_distributor");
        }
        ExcelFile file = new ExcelFile();
        new HistoryAwardFacade().setExportComissionDistributorFile(file, reportPagination);
        return new ModelAndView("ExcelView", "myModel", file);
    }
}
