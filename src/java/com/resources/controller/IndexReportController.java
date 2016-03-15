package com.resources.controller;

import com.resources.entity.CheckAwards;
import com.resources.entity.SystemTrian;
import com.resources.facade.CheckAwardsFacade;
import com.resources.facade.CustomerFacade;
import com.resources.facade.HistoryAwardFacade;
import com.resources.facade.SystemTrianFacade;
import com.resources.function.CustomFunction;
import com.resources.pagination.index.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@RequestMapping(value = "/Report")
public class IndexReportController {

    //Award    
    @RequestMapping(value = "/Award/{type}/{level}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDefaultAwardView(@PathVariable(value = "type") Integer type, @PathVariable(value = "level") Integer level, ModelMap mm, HttpSession session) {
        ReportPagination awardPagination = new ReportPagination("Lịch sử thưởng", "/Award", "/report_award", type);
        List<CheckAwards> list = new CheckAwardsFacade().findAll();
        for (CheckAwards ca : list) {
            if (type == ca.getId()) {
                awardPagination.setViewTitle(ca.getName());
            }
        }
        if (type == 15 && level == 8) {
            awardPagination.setViewTitle("Chương trình khuyến mãi");
        }
        awardPagination.setAwardType(type);
        awardPagination.setLevel(level == -1 ? null : level);
        awardPagination.setSelectedId((Integer) session.getAttribute("CUSTOMER_ID"));
        session.setAttribute("INDEX_HISTORY_AWARD_PAGINATION", awardPagination);
        return new ModelAndView(DefaultIndexPagination.CONTAINER_FOLDER + awardPagination.getViewName());
    }

    @RequestMapping(value = "/Award/DisplayPerPage/{displayPerPage}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeDisplayPerPageForAwardView(@PathVariable("displayPerPage") int displayPerPage, ModelMap mm, HttpSession session) {
        ReportPagination awardPagination = (ReportPagination) session.getAttribute("INDEX_HISTORY_AWARD_PAGINATION");
        if (awardPagination != null) {
            awardPagination.setDisplayPerPage(displayPerPage);

        }
        return customerRankCustomerView(awardPagination, session);
    }

    @RequestMapping(value = "/Award/OrderData/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView orderByAwardView(@PathVariable("orderBy") String orderBy, ModelMap mm, HttpSession session) {
        ReportPagination awardPagination = (ReportPagination) session.getAttribute("INDEX_HISTORY_AWARD_PAGINATION");
        if (awardPagination != null) {
            if (awardPagination.getOrderColmn().equals(orderBy)) {
                awardPagination.setAsc(!awardPagination.isAsc());
            }
            awardPagination.setOrderColmn(orderBy);
        }
        return customerRankCustomerView(awardPagination, session);
    }

    @RequestMapping(value = "/Award/GoTo/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoAwardView(@PathVariable("page") int page, ModelMap mm, HttpSession session) {
        ReportPagination awardPagination = (ReportPagination) session.getAttribute("INDEX_HISTORY_AWARD_PAGINATION");
        if (awardPagination != null) {
            awardPagination.setCurrentPage(page);
        }
        return customerRankCustomerView(awardPagination, session);
    }

    @RequestMapping(value = "/Award/ChangeCustomer/{cusId}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeMonthComissionDistributorView(@PathVariable("cusId") int cusId, HttpSession session) {
        ReportPagination awardPagination = (ReportPagination) session.getAttribute("INDEX_HISTORY_AWARD_PAGINATION");
        if (awardPagination == null) {
            awardPagination = new ReportPagination("Lịch sử nạp PV", "/Award", "/report_award", null);
        }
        awardPagination.setSelectedId(cusId);
        return customerRankCustomerView(awardPagination, session);
    }

    private ModelAndView customerRankCustomerView(ReportPagination awardPagination, HttpSession session) {
        if (awardPagination == null) {
            awardPagination = new ReportPagination("Lịch sử nạp PV", "/Award", "/report_award", null);
        }
        new HistoryAwardFacade().pageData(awardPagination);
        session.setAttribute("INDEX_HISTORY_AWARD_PAGINATION", awardPagination);
        return new ModelAndView(DefaultIndexPagination.AJAX_FOLDER + awardPagination.getViewName());
    }

    //Award total    
    @RequestMapping(value = "/AwardTotal", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDefaultAwardView(ModelMap mm, HttpSession session) {
        ReportPagination awardPagination = new ReportPagination("Lịch sử thưởng", "/AwardTotal", "/report_award_total", null);
        session.setAttribute("INDEX_HISTORY_AWARD_TOTAL_PAGINATION", awardPagination);
        return new ModelAndView(DefaultIndexPagination.CONTAINER_FOLDER + awardPagination.getViewName());
    }

    @RequestMapping(value = "/AwardTotal/DisplayPerPage/{displayPerPage}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeDisplayPerPageForAwardTotalView(@PathVariable("displayPerPage") int displayPerPage, ModelMap mm, HttpSession session) {
        ReportPagination awardPagination = (ReportPagination) session.getAttribute("INDEX_HISTORY_AWARD_TOTAL_PAGINATION");
        if (awardPagination != null) {
            awardPagination.setDisplayPerPage(displayPerPage);
        }
        return customerRankCustomerTotalView(awardPagination, session);
    }

    @RequestMapping(value = "/AwardTotal/OrderData/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView orderByAwardTotalView(@PathVariable("orderBy") String orderBy, ModelMap mm, HttpSession session) {
        ReportPagination awardPagination = (ReportPagination) session.getAttribute("INDEX_HISTORY_AWARD_TOTAL_PAGINATION");
        if (awardPagination != null) {
            if (awardPagination.getOrderColmn().equals(orderBy)) {
                awardPagination.setAsc(!awardPagination.isAsc());
            }
            awardPagination.setOrderColmn(orderBy);
        }
        return customerRankCustomerTotalView(awardPagination, session);
    }

    @RequestMapping(value = "/AwardTotal/GoTo/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoAwardTotalView(@PathVariable("page") int page, ModelMap mm, HttpSession session) {
        ReportPagination awardPagination = (ReportPagination) session.getAttribute("INDEX_HISTORY_AWARD_TOTAL_PAGINATION");
        if (awardPagination != null) {
            awardPagination.setCurrentPage(page);
        }
        return customerRankCustomerTotalView(awardPagination, session);
    }

    @RequestMapping(value = "/AwardTotal/ChangeYear/{year}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeYearComissionDistributorTotalView(@PathVariable("year") int year, HttpSession session) {
        ReportPagination awardPagination = (ReportPagination) session.getAttribute("INDEX_HISTORY_AWARD_TOTAL_PAGINATION");
        if (awardPagination == null) {
            awardPagination = new ReportPagination("Lịch sử nạp PV", "/AwardTotal", "/report_award_total", null);
        }
        awardPagination.setYear(year);
        return customerRankCustomerTotalView(awardPagination, session);
    }

    private ModelAndView customerRankCustomerTotalView(ReportPagination awardPagination, HttpSession session) {
        if (awardPagination == null) {
            awardPagination = new ReportPagination("Lịch sử nạp PV", "/AwardTotal", "/report_award_total", null);
        }
        new HistoryAwardFacade().pageDataByMonth(awardPagination, (Integer) session.getAttribute("CUSTOMER_ID"));
        session.setAttribute("INDEX_HISTORY_AWARD_TOTAL_PAGINATION", awardPagination);
        return new ModelAndView(DefaultIndexPagination.AJAX_FOLDER + awardPagination.getViewName());
    }

    //Award G    
    @RequestMapping(value = "/AwardG", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDefaultAwardGView(ModelMap mm, HttpSession session) {
        ReportPagination awardPagination = new ReportPagination("Thống kê thành tích", "/AwardG", "/report_award_g", null);
        awardPagination.setSelectedId((Integer) session.getAttribute("CUSTOMER_ID"));
        session.setAttribute("INDEX_HISTORY_AWARD_G_PAGINATION", awardPagination);
        return new ModelAndView(DefaultIndexPagination.CONTAINER_FOLDER + awardPagination.getViewName());
    }

    @RequestMapping(value = "/AwardG/GoTo/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoAwardGView(@PathVariable("page") int page, ModelMap mm, HttpSession session) {
        ReportPagination awardPagination = (ReportPagination) session.getAttribute("INDEX_HISTORY_AWARD_G_PAGINATION");
        if (awardPagination != null) {
            awardPagination.setCurrentPage(page);
        }
        return customerRankCustomerGView(awardPagination, session);
    }

    @RequestMapping(value = "/AwardG/ChangeCustomer/{cusId}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeYearComissionDistributorGView(@PathVariable("cusId") int cusId, HttpSession session, ModelMap mm) {
        ReportPagination awardPagination = (ReportPagination) session.getAttribute("INDEX_HISTORY_AWARD_G_PAGINATION");
        if (awardPagination == null) {
            awardPagination = new ReportPagination("Thống kê thành tích", "/AwardG", "/report_award_g", null);
        }
        awardPagination.setSelectedId(cusId);
        mm.put("COUNT_MEMBER", new CustomerFacade().countMember(cusId));
        return customerRankCustomerGView(awardPagination, session);
    }

    private ModelAndView customerRankCustomerGView(ReportPagination awardPagination, HttpSession session) {
        if (awardPagination == null) {
            awardPagination = new ReportPagination("Thống kê thành tích", "/AwardG", "/report_award_g", null);
        }
        new HistoryAwardFacade().pageDataGByMonth(awardPagination);
        session.setAttribute("INDEX_HISTORY_AWARD_G_PAGINATION", awardPagination);
        return new ModelAndView(DefaultIndexPagination.AJAX_FOLDER + awardPagination.getViewName());
    }

    //Trian   
    @RequestMapping(value = "/Trian", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDefaultTrianView(ModelMap mm, HttpSession session) {
        ReportPagination awardPagination = new ReportPagination("Thống kê tri ân", "/Trian", "/report_trian", null);
        awardPagination.setInsertViewName("/report_trian_insert_modal");
        session.setAttribute("INDEX_HISTORY_TRIAN_PAGINATION", awardPagination);
        return new ModelAndView(DefaultIndexPagination.CONTAINER_FOLDER + awardPagination.getViewName());
    }

    @RequestMapping(value = "/Trian/DisplayPerPage/{displayPerPage}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeDisplayPerPageForTrianView(@PathVariable("displayPerPage") int displayPerPage, ModelMap mm, HttpSession session) {
        ReportPagination awardPagination = (ReportPagination) session.getAttribute("INDEX_HISTORY_TRIAN_PAGINATION");
        if (awardPagination != null) {
            awardPagination.setDisplayPerPage(displayPerPage);

        }
        return trianView(awardPagination, session);
    }

    @RequestMapping(value = "/Trian/OrderData/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView orderByTrianView(@PathVariable("orderBy") String orderBy, ModelMap mm, HttpSession session) {
        ReportPagination awardPagination = (ReportPagination) session.getAttribute("INDEX_HISTORY_TRIAN_PAGINATION");
        if (awardPagination != null) {
            if (awardPagination.getOrderColmn().equals(orderBy)) {
                awardPagination.setAsc(!awardPagination.isAsc());
            }
            awardPagination.setOrderColmn(orderBy);
        }
        return trianView(awardPagination, session);
    }

    @RequestMapping(value = "/Trian/GoTo/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoTrianView(@PathVariable("page") int page, ModelMap mm, HttpSession session) {
        ReportPagination awardPagination = (ReportPagination) session.getAttribute("INDEX_HISTORY_TRIAN_PAGINATION");
        if (awardPagination != null) {
            awardPagination.setCurrentPage(page);
        }
        return trianView(awardPagination, session);
    }

    @RequestMapping(value = "/Trian/ChangeYear/{year}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeYearTrianGView(@PathVariable("year") int year, HttpSession session) {
        ReportPagination awardPagination = (ReportPagination) session.getAttribute("INDEX_HISTORY_TRIAN_PAGINATION");
        if (awardPagination == null) {
            awardPagination = new ReportPagination("Thống kê tri ân", "/Trian", "/report_trian", null);
        }
        awardPagination.setYear(year);
        return trianView(awardPagination, session);
    }

    private ModelAndView trianView(ReportPagination awardPagination, HttpSession session) {
        if (awardPagination == null) {
            awardPagination = new ReportPagination("Thống kê tri ân", "/Trian", "/report_trian", null);
        }
        awardPagination.setOrderColmn("datetimecreated");
        new HistoryAwardFacade().pageDataTrian(awardPagination, (Integer) session.getAttribute("CUSTOMER_ID"));
        session.setAttribute("INDEX_HISTORY_TRIAN_PAGINATION", awardPagination);
        return new ModelAndView(DefaultIndexPagination.AJAX_FOLDER + awardPagination.getViewName());
    }

    @RequestMapping(value = "/Trian/ViewInsert", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getTrianInsertView(ModelMap mm, HttpSession session) {
        ReportPagination awardPagination = (ReportPagination) session.getAttribute("INDEX_HISTORY_TRIAN_PAGINATION");
        if (awardPagination == null) {
            awardPagination = new ReportPagination("Thống kê tri ân", "/Trian", "/report_trian", null);
            awardPagination.setInsertViewName("/report_trian_insert_modal");
        }
        return new ModelAndView(DefaultIndexPagination.AJAX_FOLDER + awardPagination.getInsertViewName());
    }

    @RequestMapping(value = "/Trian/Insert", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView insertTrian(@RequestBody Map map, ModelMap mm, HttpServletRequest request) {
        ModelAndView mAV = new ModelAndView(DefaultIndexPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        HttpSession session = request.getSession();
        Map captchaList = (Map) session.getAttribute("CAPTCHA");
        String triAnCaptcha = (String) captchaList.get("TRIAN");
        String checkCaptcha = ((String) map.get("captcha")).toLowerCase();
        String checkPinCode = (String) map.get("pinCode");
        String userPinCode = (String) session.getAttribute("CUSTOMER_PIN_CODE");
        Integer userId = (Integer) session.getAttribute("CUSTOMER_ID");
        Date customerActiveTime = (Date) session.getAttribute("CUSTOMER_ACTIVE_TIME");
        Boolean checkTrianTime = CustomFunction.checkTrianTime((String) request.getServletContext().getAttribute("MAIN_PROPERTIES_FILE_PATH"), customerActiveTime);
        Date currentTime = new Date();
        Long trianTime = null;
        try {
            trianTime = CustomFunction.getTrianTime((String) request.getServletContext().getAttribute("MAIN_PROPERTIES_FILE_PATH"));
        } catch (ParseException ex) {
            Logger.getLogger(IndexReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (currentTime.getTime() < trianTime) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Chưa đến thời gian tham gia chương trình!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        } else if (checkCaptcha == null || !CustomFunction.md5(checkCaptcha).equals(triAnCaptcha)) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Mã captcha không hợp lệ!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        } else if (!checkTrianTime) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Bạn không đủ điều kiện tham gia chương trình!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        } else if (!checkPinCode.equals(userPinCode)) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Mã PIN không hợp lệ!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        try {
            new HistoryAwardFacade().joinTrian(userId, userPinCode, currentTime);
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "Thành công", "Tham gia chương trình thành công vào hồi "+new SimpleDateFormat("HH:mm:ss.SSS dd/MM/yyyy").format(currentTime)+"!"
                        + "<script>$(document).ready(function() {"
                        + "$('#ajax-content').html('');"
                        + "$('.item-join-trian').remove();"
                        + "$('.item-tree-trian').removeClass('hidden');"
                        + "$('.item-tree-trian a').trigger('click')"
                        + "});</script>");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
        } catch (Exception e) {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi vui lòng thử lại sau!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
        }
    }

    //Tree Tri an
    @RequestMapping(value = "/Trian/TreeTrian", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getTreeCustomer(ModelMap mm, HttpSession session) {
        Integer cusid = (Integer) session.getAttribute("CUSTOMER_ID");
        SystemTrian myTrian = new SystemTrianFacade().findByCustomerId(cusid);
        List tree = null;
        try {
            tree = new SystemTrianFacade().getTreeTrian(cusid);
            mm.put("TREE_TRIAN", CustomFunction.buildTreeTrian(tree, myTrian.getParentPos()));
        } catch (Exception ex) {
            mm.put("TREE_TRIAN", null);
        }
        return new ModelAndView(DefaultIndexPagination.AJAX_FOLDER + "/report_trian_tree");
    }
}
