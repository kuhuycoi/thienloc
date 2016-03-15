package com.resources.controller;

import com.resources.bean.ExcelFile;
import com.resources.bean.GratefulCondition;
import com.resources.entity.Admin;
import com.resources.facade.AdminFacade;
import com.resources.facade.CustomerFacade;
import com.resources.facade.SystemTrianFacade;
import com.resources.pagination.admin.DefaultAdminPagination;
import com.resources.pagination.admin.GratefulPagination;
import com.resources.pagination.admin.MessagePagination;
import com.resources.utils.ConfigUtils;
import com.resources.utils.StringUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
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
@RequestMapping(value = "/Admin/Grateful")
public class AdminGratefulController {

    //TRIAN
    @RequestMapping(value = "/Trian", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDefaultTrianView(ModelMap mm, HttpServletRequest request) {
        HttpSession session = request.getSession();
        GratefulPagination pagination = new GratefulPagination("/grateful_trian", "/Trian", "grateful_trian", "parentPos");
        session.setAttribute("ADMIN_GRATEFUL_TRIAN_PAGINATION", pagination);
        return new ModelAndView(DefaultAdminPagination.CONTAINER_FOLDER + pagination.getViewName());
    }

    @RequestMapping(value = "/Trian/Search", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public ModelAndView searchDistributorView(@RequestBody Map map, HttpServletRequest request) {
        String searchString = (String) map.get("searchString");
        if (StringUtils.isEmpty(searchString)) {
            return customerListView(null, request);
        }
        List<String> keywords = (List) map.get("keywords");
        GratefulPagination pagination = new GratefulPagination("/grateful_trian", "/Trian", "grateful_trian", "parentPos");
        pagination.setSearchString(searchString);
        pagination.setKeywords(keywords);
        return trianView(pagination, request);
    }

    @RequestMapping(value = "/Trian/DisplayPerPage/{displayPerPage}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeDisplayPerPageForTrianView(@PathVariable("displayPerPage") int displayPerPage, ModelMap mm, HttpServletRequest request) {
        HttpSession session = request.getSession();
        GratefulPagination pagination = (GratefulPagination) session.getAttribute("ADMIN_GRATEFUL_TRIAN_PAGINATION");
        if (pagination != null) {
            pagination.setDisplayPerPage(displayPerPage);
        }
        return trianView(pagination, request);
    }

    @RequestMapping(value = "/Trian/OrderData/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView orderByTrianView(@PathVariable("orderBy") String orderBy, ModelMap mm, HttpServletRequest request) {
        HttpSession session = request.getSession();
        GratefulPagination pagination = (GratefulPagination) session.getAttribute("ADMIN_GRATEFUL_TRIAN_PAGINATION");
        if (pagination != null) {
            if (pagination.getOrderColmn().equals(orderBy)) {
                pagination.setAsc(!pagination.isAsc());
            }
            pagination.setOrderColmn(orderBy);
        }
        return trianView(pagination, request);
    }

    @RequestMapping(value = "/Trian/GoTo/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoTrianView(@PathVariable("page") int page, ModelMap mm, HttpServletRequest request) {
        HttpSession session = request.getSession();
        GratefulPagination pagination = (GratefulPagination) session.getAttribute("ADMIN_GRATEFUL_TRIAN_PAGINATION");
        if (pagination != null) {
            pagination.setCurrentPage(page);
        }
        return trianView(pagination, request);
    }

    @RequestMapping(value = "/Trian/ChangeAgency/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeAgencyTrianView(@PathVariable("id") int id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        GratefulPagination pagination = (GratefulPagination) session.getAttribute("ADMIN_GRATEFUL_TRIAN_PAGINATION");
        if (id == -1) {
            pagination.setAgencyId(null);
        } else {
            pagination.setAgencyId(id);
        }
        Admin admin = new AdminFacade().findAdminById((Integer) session.getAttribute("ADMIN_ID"));
        if (admin.getRoleAdmID().getId() == 2 && admin.getProvincialAgencyID().getId() != 0) {
            pagination.setAgencyId(admin.getProvincialAgencyID().getId());
        }
        pagination.setCurrentPage(1);
        return trianView(pagination, request);
    }

    private ModelAndView trianView(GratefulPagination pagination, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (pagination == null) {
            pagination = new GratefulPagination("/grateful_trian", "/Trian", "Danh sách tri ân", "parentPos");
            Admin admin = new AdminFacade().findAdminById((Integer) request.getSession().getAttribute("ADMIN_ID"));
            if (admin.getRoleAdmID().getId() == 2 && admin.getProvincialAgencyID().getId() != 0) {
                pagination.setAgencyId(admin.getProvincialAgencyID().getId());
            }
        }
        new SystemTrianFacade().pageData(pagination);
        session.setAttribute("ADMIN_GRATEFUL_TRIAN_PAGINATION", pagination);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + pagination.getViewName());
    }

    //CUSTOMER lIST
    @RequestMapping(value = "/CustomerList", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDefaultCustomerListView(ModelMap mm, HttpServletRequest request) {
        HttpSession session = request.getSession();
        GratefulPagination pagination = new GratefulPagination("/CustomerList");
        pagination.setInsertViewName("/report_trian_insert_modal");
        try {
            pagination.setStartDate(new SimpleDateFormat("dd/MM/yyyy").parse(ConfigUtils.getInstance().getProperty((String) request.getServletContext().getAttribute("MAIN_PROPERTIES_FILE_PATH"), "trian.time.start")));
            pagination.setEndDate(new SimpleDateFormat("dd/MM/yyyy").parse(ConfigUtils.getInstance().getProperty((String) request.getServletContext().getAttribute("MAIN_PROPERTIES_FILE_PATH"), "trian.time.finish")));
        } catch (ParseException ex) {
            Logger.getLogger(AdminGratefulController.class.getName()).log(Level.SEVERE, null, ex);
        }
        session.setAttribute("ADMIN_GRATEFUL_PAGINATION", pagination);
        return new ModelAndView(DefaultAdminPagination.CONTAINER_FOLDER + pagination.getViewName());
    }

    @RequestMapping(value = "/CustomerList/Search", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public ModelAndView searchCustomerListView(@RequestBody Map map, HttpServletRequest request) {
        String searchString = (String) map.get("searchString");
        if (StringUtils.isEmpty(searchString)) {
            return customerListView(null, request);
        }
        List<String> keywords = (List) map.get("keywords");
        GratefulPagination distributorPagination = new GratefulPagination("/CustomerList");
        distributorPagination.setSearchString(searchString);
        distributorPagination.setKeywords(keywords);
        return customerListView(distributorPagination, request);
    }

    @RequestMapping(value = "/CustomerList/DisplayPerPage/{displayPerPage}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeDisplayPerPageForCustomerListView(@PathVariable("displayPerPage") int displayPerPage, ModelMap mm, HttpServletRequest request) {
        HttpSession session = request.getSession();
        GratefulPagination pagination = (GratefulPagination) session.getAttribute("ADMIN_GRATEFUL_PAGINATION");
        if (pagination != null) {
            pagination.setDisplayPerPage(displayPerPage);
        }
        return customerListView(pagination, request);
    }

    @RequestMapping(value = "/CustomerList/OrderData/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView orderByCustomerListView(@PathVariable("orderBy") String orderBy, ModelMap mm, HttpServletRequest request) {
        HttpSession session = request.getSession();
        GratefulPagination pagination = (GratefulPagination) session.getAttribute("ADMIN_GRATEFUL_PAGINATION");
        if (pagination != null) {
            if (pagination.getOrderColmn().equals(orderBy)) {
                pagination.setAsc(!pagination.isAsc());
            }
            pagination.setOrderColmn(orderBy);
        }
        return customerListView(pagination, request);
    }

    @RequestMapping(value = "/CustomerList/GoTo/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoCustomerListView(@PathVariable("page") int page, ModelMap mm, HttpServletRequest request) {
        HttpSession session = request.getSession();
        GratefulPagination pagination = (GratefulPagination) session.getAttribute("ADMIN_GRATEFUL_PAGINATION");
        if (pagination != null) {
            pagination.setCurrentPage(page);
        }
        return customerListView(pagination, request);
    }

    private ModelAndView customerListView(GratefulPagination pagination, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (pagination == null) {
            pagination = new GratefulPagination("/CustomerList");
            Admin admin = new AdminFacade().findAdminById((Integer) request.getSession().getAttribute("ADMIN_ID"));
            if (admin.getRoleAdmID().getId() == 2 && admin.getProvincialAgencyID().getId() != 0) {
                pagination.setAgencyId(admin.getProvincialAgencyID().getId());
            }
            try {
                pagination.setStartDate(new SimpleDateFormat("dd/MM/yyyy").parse(ConfigUtils.getInstance().getProperty((String) request.getServletContext().getAttribute("MAIN_PROPERTIES_FILE_PATH"), "trian.time.start")));
                pagination.setEndDate(new SimpleDateFormat("dd/MM/yyyy").parse(ConfigUtils.getInstance().getProperty((String) request.getServletContext().getAttribute("MAIN_PROPERTIES_FILE_PATH"), "trian.time.finish")));
            } catch (ParseException ex) {
                Logger.getLogger(AdminGratefulController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        new CustomerFacade().pageDataGrateful(pagination);
        session.setAttribute("ADMIN_GRATEFUL_PAGINATION", pagination);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + pagination.getViewName());
    }

    @RequestMapping(value = "/CustomerList/ChangeAgency/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeAgencyCustomerListView(@PathVariable("id") int id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        GratefulPagination pagination = (GratefulPagination) session.getAttribute("ADMIN_GRATEFUL_PAGINATION");
        if (id == -1) {
            pagination.setAgencyId(null);
        } else {
            pagination.setAgencyId(id);
        }
        Admin admin = new AdminFacade().findAdminById((Integer) session.getAttribute("ADMIN_ID"));
        if (admin.getRoleAdmID().getId() == 2 && admin.getProvincialAgencyID().getId() != 0) {
            pagination.setAgencyId(admin.getProvincialAgencyID().getId());
        }
        pagination.setCurrentPage(1);
        return customerListView(pagination, request);
    }

    @RequestMapping(value = "/CustomerList/HasPinCode/{status}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeDayCustomerListView(@PathVariable("status") int status, HttpServletRequest request) {
        HttpSession session = request.getSession();
        GratefulPagination pagination = (GratefulPagination) session.getAttribute("ADMIN_GRATEFUL_PAGINATION");
        Boolean hasPincode;
        switch (status) {
            case 0: {
                hasPincode = false;
                break;
            }
            case 1: {
                hasPincode = true;
                break;
            }
            default: {
                hasPincode = null;
                break;
            }
        }
        pagination.setHasPinCode(hasPincode);
        pagination.setCurrentPage(1);
        return customerListView(pagination, request);
    }

    @RequestMapping(value = "/CustomerList/Insert/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView resetPassword(@PathVariable Integer id, ModelMap mm) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        String listCusId = "";
        try {
            listCusId += id;
            new CustomerFacade().updatePincode(listCusId);
        } catch (Exception e) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Vui lòng thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Cập nhật mã PIN thành công!");
        mm.put("MESSAGE_PAGINATION", mP);
        return mAV;
    }

    @RequestMapping(value = "/CustomerList/Insert", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView resetPassword(@RequestBody Integer[] ids, ModelMap mm) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        if (ids == null || ids.length == 0) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Vui lòng chọn ít nhất 1 NPP!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        String listCusId = "";
        try {
            for (Integer id : ids) {
                listCusId += "," + id;
            }
            listCusId = listCusId.substring(1);
            new CustomerFacade().updatePincode(listCusId);
        } catch (Exception e) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Vui lòng thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Cập nhật mã PIN thành công!");
        mm.put("MESSAGE_PAGINATION", mP);
        return mAV;
    }

    @RequestMapping(value = "/CustomerList/Export", method = RequestMethod.GET)
    public ModelAndView exportComissionDistributorView(HttpSession session) {
        GratefulPagination pagination = (GratefulPagination) session.getAttribute("ADMIN_GRATEFUL_PAGINATION");
        if (pagination == null) {
            pagination = new GratefulPagination("/CustomerList");
        }
        ExcelFile file = new ExcelFile();
        new CustomerFacade().setExportCustomerPinCodeFile(file, pagination);
        return new ModelAndView("ExcelView", "myModel", file);
    }

    @RequestMapping(value = "/Condition", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDefaultConditionTrianView(ModelMap mm, HttpServletRequest request) {

        HttpSession session = request.getSession();
        GratefulPagination pagination = new GratefulPagination("/grateful_condition", "/Condition", "Điều kiện tri ân", "parentPos");
        session.setAttribute("ADMIN_GRATEFUL_CONDITION_PAGINATION", pagination);
        GratefulCondition condition = new GratefulCondition();
        String trianTimeExacly = ConfigUtils.getInstance().getProperty((String) request.getServletContext().getAttribute("MAIN_PROPERTIES_FILE_PATH"), "trian.time.exacly");
        String trianTimeStart = ConfigUtils.getInstance().getProperty((String) request.getServletContext().getAttribute("MAIN_PROPERTIES_FILE_PATH"), "trian.time.start");
        String trianTimeFinish = ConfigUtils.getInstance().getProperty((String) request.getServletContext().getAttribute("MAIN_PROPERTIES_FILE_PATH"), "trian.time.finish");

        condition.setActiveFinishDate(trianTimeFinish);
        condition.setActiveStartDate(trianTimeStart);
        condition.setJoinGratefulDate(trianTimeExacly);
        mm.put("GRATEFUL_CONDITION", condition);
        return new ModelAndView(DefaultAdminPagination.CONTAINER_FOLDER + pagination.getViewName());
    }

    @RequestMapping(value = "/Condition/Edit", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView editConditionTrianView(@RequestBody GratefulCondition condition, ModelMap mm, HttpServletRequest request) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            dateFormat.parse(condition.getActiveStartDate());
        } catch (Exception e) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "lỗi", "Điều kiện tham gia tri ân - Ngày bắt đầu không đúng định dạng. Định dạng yêu cầu: dd/mm/yyyy. VD: 20/01/2016!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        try {
            dateFormat.parse(condition.getActiveFinishDate());
        } catch (Exception e) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "lỗi", "Điều kiện tham gia tri ân - Ngày kết thúc không đúng định dạng. Định dạng yêu cầu: dd/mm/yyyy. VD: 20/01/2016!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        try {
            timeFormat.parse(condition.getJoinGratefulDate());
        } catch (Exception e) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "lỗi", "Điều kiện bắt đầu tri ân - Thời gian đếm ngược không đúng định dạng. Định dạng yêu cầu: dd/mm/yyyy hh:mm:ss. VD: 20/01/2016 19:45:30!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        try {
            if (dateFormat.parse(condition.getActiveStartDate()).compareTo(dateFormat.parse(condition.getActiveFinishDate())) > 0) {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "lỗi", "Điều kiện tham gia tri ân - Ngày bắt đầu phải ở trước hoặc cùng ngày kết thúc!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
        } catch (Exception e) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "lỗi", "Đã xảy ra lỗi! Vui lòng thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        try {
            ConfigUtils.getInstance().setProperty((String) request.getServletContext().getAttribute("MAIN_PROPERTIES_FILE_PATH"), "trian.time.exacly", condition.getJoinGratefulDate());
            ConfigUtils.getInstance().setProperty((String) request.getServletContext().getAttribute("MAIN_PROPERTIES_FILE_PATH"), "trian.time.start", condition.getActiveStartDate());
            ConfigUtils.getInstance().setProperty((String) request.getServletContext().getAttribute("MAIN_PROPERTIES_FILE_PATH"), "trian.time.finish", condition.getActiveFinishDate());
        } catch (Exception e) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "lỗi", "Đã xảy ra lỗi! Vui lòng thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Cập nhật điều kiện tri ân thành công!");
        mm.put("MESSAGE_PAGINATION", mP);
        return mAV;
    }
}
