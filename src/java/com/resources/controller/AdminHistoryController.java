package com.resources.controller;

import com.google.gson.Gson;
import com.resources.entity.Customer;
import com.resources.entity.RankCustomes;
import com.resources.facade.AdminFacade;
import com.resources.facade.CheckAwardsFacade;
import com.resources.facade.CustomerFacade;
import com.resources.facade.CustomerRankCustomerFacade;
import com.resources.facade.HistoryAwardFacade;
import com.resources.facade.RankCustomersFacade;
import com.resources.pagination.admin.HistoryPagination;
import com.resources.pagination.admin.DefaultAdminPagination;
import com.resources.pagination.admin.MessagePagination;
import com.resources.pagination.admin.ReportPagination;
import com.resources.utils.LeoConstants;
import com.resources.utils.LogUtils;
import com.resources.utils.StringUtils;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/Admin/History")
public class AdminHistoryController {

    //CustomerRankCustomer    
    @RequestMapping(value = "/CustomerRankCustomer", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDefaultCustomerRankCustomerView(ModelMap mm, HttpSession session) {
        HistoryPagination customerRankCustomerPagination = (HistoryPagination) session.getAttribute("CUSTOMER_RANK_CUSTOMER_PAGINATION");
        if (customerRankCustomerPagination == null) {
            customerRankCustomerPagination = new HistoryPagination("Lịch sử nạp PV", "/CustomerRankCustomer", "/history_customer_rank_customer");
        }
        session.setAttribute("CUSTOMER_RANK_CUSTOMER_PAGINATION", customerRankCustomerPagination);
        return new ModelAndView(DefaultAdminPagination.CONTAINER_FOLDER + customerRankCustomerPagination.getViewName());
    }

    @RequestMapping(value = "/CustomerRankCustomer/DisplayPerPage/{displayPerPage}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeDisplayPerPageForCustomerRankCustomerView(@PathVariable("displayPerPage") int displayPerPage, ModelMap mm, HttpSession session) {
        HistoryPagination customerRankCustomerPagination = (HistoryPagination) session.getAttribute("CUSTOMER_RANK_CUSTOMER_PAGINATION");
        if (customerRankCustomerPagination != null) {
            customerRankCustomerPagination.setDisplayPerPage(displayPerPage);
        }
        return customerRankCustomerView(customerRankCustomerPagination, session);
    }

    @RequestMapping(value = "/CustomerRankCustomer/OrderData/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView orderByCustomerRankCustomerView(@PathVariable("orderBy") String orderBy, ModelMap mm, HttpSession session) {
        HistoryPagination customerRankCustomerPagination = (HistoryPagination) session.getAttribute("CUSTOMER_RANK_CUSTOMER_PAGINATION");
        if (customerRankCustomerPagination != null) {
            if (customerRankCustomerPagination.getOrderColmn().equals(orderBy)) {
                customerRankCustomerPagination.setAsc(!customerRankCustomerPagination.isAsc());
            }
            customerRankCustomerPagination.setOrderColmn(orderBy);
        }
        return customerRankCustomerView(customerRankCustomerPagination, session);
    }

    @RequestMapping(value = "/CustomerRankCustomer/GoTo/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoCustomerRankCustomerView(@PathVariable("page") int page, ModelMap mm, HttpSession session) {
        HistoryPagination customerRankCustomerPagination = (HistoryPagination) session.getAttribute("CUSTOMER_RANK_CUSTOMER_PAGINATION");
        if (customerRankCustomerPagination != null) {
            customerRankCustomerPagination.setCurrentPage(page);
        }
        return customerRankCustomerView(customerRankCustomerPagination, session);
    }

    @RequestMapping(value = "/CustomerRankCustomer/Search", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public ModelAndView searchDistributorView(@RequestBody Map map, HttpSession session) {
        String searchString = (String) map.get("searchString");
        if (StringUtils.isEmpty(searchString)) {
            return customerRankCustomerView(null, session);
        }
        List<String> keywords = (List) map.get("keywords");
        HistoryPagination customerRankCustomerPagination = new HistoryPagination("Lịch sử nạp PV", "/CustomerRankCustomer", "/history_customer_rank_customer");
        customerRankCustomerPagination.setSearchString(searchString);
        customerRankCustomerPagination.setKeywords(keywords);
        return customerRankCustomerView(customerRankCustomerPagination, session);
    }

    @RequestMapping(value = "/CustomerRankCustomer/ChangeAgency/{agencyId}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeAgencyView(@PathVariable("agencyId") Integer agencyId, HttpSession session) {
        HistoryPagination customerRankCustomerPagination = new HistoryPagination("Lịch sử nạp PV", "/CustomerRankCustomer", "/history_customer_rank_customer");
        customerRankCustomerPagination.setAgencyId(agencyId == -1 ? null : agencyId);
        session.setAttribute("CUSTOMER_RANK_CUSTOMER_PAGINATION", customerRankCustomerPagination);
        return customerRankCustomerView(customerRankCustomerPagination, session);
    }

    @RequestMapping(value = "/CustomerRankCustomer/ChangeDay/{type}/{day}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeDayComissionDistributorView(@PathVariable("type") int type, @PathVariable("day") @DateTimeFormat(pattern = "yyyy-mm-dd") Date day, HttpSession session) {
        HistoryPagination customerRankCustomerPagination = new HistoryPagination("Lịch sử nạp PV", "/CustomerRankCustomer", "/history_customer_rank_customer");
        day = "-1".equals(day) ? null : day;
        if (type == 0) {
            customerRankCustomerPagination.setStartDate(day);
        } else {
            customerRankCustomerPagination.setEndDate(day);
        }
        return customerRankCustomerView(customerRankCustomerPagination, session);
    }

    private ModelAndView customerRankCustomerView(HistoryPagination customerRankCustomerPagination, HttpSession session) {
        if (customerRankCustomerPagination == null) {
            customerRankCustomerPagination = new HistoryPagination("Lịch sử nạp PV", "/CustomerRankCustomer", "/history_customer_rank_customer");
        }
        Integer role = new AdminFacade().getAdminRoleByAdminId((Integer) session.getAttribute("ADMIN_ID"));
        Integer agencyId = 0;
        if (role == 2) {
            agencyId = new AdminFacade().getAdminAgencyByAdminId((Integer) session.getAttribute("ADMIN_ID"));
        }
        new CustomerRankCustomerFacade().pageData(customerRankCustomerPagination, role, agencyId);
        session.setAttribute("CUSTOMER_RANK_CUSTOMER_PAGINATION", customerRankCustomerPagination);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + customerRankCustomerPagination.getViewName());
    }

    @RequestMapping(value = "/CustomerRankCustomer/ViewInsert", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getNeverUpRankViewInsert(HttpSession session) {
        HistoryPagination neverUpRankPagination = (HistoryPagination) session.getAttribute("CUSTOMER_RANK_CUSTOMER_PAGINATION");
        if (neverUpRankPagination == null) {
            neverUpRankPagination = new HistoryPagination("Danh sách chưa từng nạp PV", "id", true, "/NeverUpRank", "/history_never_up_rank");
        }
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + neverUpRankPagination.getInsertViewName());
    }

    @RequestMapping(value = "/CustomerRankCustomer/Insert", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView insertCustomerRankCustomer(@RequestParam(value = "userName") String userName,
            @RequestParam(value = "rankCustomerId") Integer rankCustomerId,
            @RequestParam(value = "multipleGrateful", required = false) Integer multipleGrateful,
            ModelMap mm, HttpSession session) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        Integer result = 0;
//        if (rankCustomerId == 2 || rankCustomerId == 3) {
//            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Gói PV này chưa đi vào hoạt động!");
//            mm.put("MESSAGE_PAGINATION", mP);
//            return mAV;
//        }    
        Integer role = new AdminFacade().getAdminRoleByAdminId((Integer) session.getAttribute("ADMIN_ID"));
        Customer c = new CustomerFacade().findCustomerByUsername(userName);
        try {
            if (role == 1) {
                result = new CustomerRankCustomerFacade().depositPv(userName, rankCustomerId, multipleGrateful, (Integer) session.getAttribute("ADMIN_ID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_NAPTIEN, 4, "Nạp tiền vào tài khoản: '" + c.getUserName() + "', gói: '" + rankCustomerId + "', thành công: 'Không'");
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Vui lòng thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        switch (result) {
            case 1: {
                LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_NAPTIEN, 4, "Nạp tiền vào tài khoản: '" + c.getUserName() + "', gói: '" + rankCustomerId + "', lỗi: 'Tài khoản đã từng nạp gói PV này!'");
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Tài khoản đã từng nạp gói PV này!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 2: {
                LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_NAPTIEN, 4, "Nạp tiền vào tài khoản: '" + c.getUserName() + "', gói: '" + rankCustomerId + "', thành công!'");
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Nạp PV thành công!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 3: {
                LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_NAPTIEN, 4, "Nạp tiền vào tài khoản: '" + c.getUserName() + "', gói: '" + rankCustomerId + "', lỗi: 'Yêu cầu nạp gói 300PV trước!'");
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Yêu cầu nạp gói 300PV trước!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            default: {
                LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_NAPTIEN, 4, "Nạp tiền vào tài khoản: '" + c.getUserName() + "', gói: '" + rankCustomerId + "', lỗi: '??????'");
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Vui lòng thử lại sau!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
        }
    }

    //Award  
    @RequestMapping(value = "/Award", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDefaultAwardView(HttpSession session) {
        HistoryPagination awardPagination = (HistoryPagination) session.getAttribute("HISTORY_AWARD_PAGINATION");
        if (awardPagination == null) {
            awardPagination = new HistoryPagination("Lịch sử thưởng", "/Award", "/history_award");
        }
        session.setAttribute("HISTORY_AWARD_PAGINATION", awardPagination);
        return new ModelAndView(DefaultAdminPagination.CONTAINER_FOLDER + awardPagination.getViewName());
    }

    @RequestMapping(value = "/Award/DisplayPerPage/{displayPerPage}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeDisplayPerPageForAwardView(@PathVariable("displayPerPage") int displayPerPage, HttpSession session) {
        HistoryPagination awardPagination = (HistoryPagination) session.getAttribute("HISTORY_AWARD_PAGINATION");
        if (awardPagination != null) {
            awardPagination.setDisplayPerPage(displayPerPage);
        }
        return awardView(awardPagination, session);
    }

    @RequestMapping(value = "/Award/OrderData/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView orderByAwardView(@PathVariable("orderBy") String orderBy, ModelMap mm, HttpSession session) {
        HistoryPagination awardPagination = (HistoryPagination) session.getAttribute("HISTORY_AWARD_PAGINATION");
        if (awardPagination != null) {
            if (awardPagination.getOrderColmn().equals(orderBy)) {
                awardPagination.setAsc(!awardPagination.isAsc());
            }
            awardPagination.setOrderColmn(orderBy);
        }
        return awardView(awardPagination, session);
    }

    @RequestMapping(value = "/Award/GoTo/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoAwardView(@PathVariable("page") int page, ModelMap mm, HttpSession session) {
        HistoryPagination awardPagination = (HistoryPagination) session.getAttribute("HISTORY_AWARD_PAGINATION");
        if (awardPagination != null) {
            awardPagination.setCurrentPage(page);
        }
        return awardView(awardPagination, session);
    }

    @RequestMapping(value = "/Award/Search", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public ModelAndView searchAwardView(@RequestBody Map map, HttpSession session) {
        String searchString = (String) map.get("searchString");
        if (StringUtils.isEmpty(searchString)) {
            return awardView(null, session);
        }
        List<String> keywords = (List) map.get("keywords");
        HistoryPagination awardPagination = new HistoryPagination("Lịch sử thưởng", "/Award", "/history_award");
        awardPagination.setSearchString(searchString);
        awardPagination.setKeywords(keywords);
        return awardView(awardPagination, session);
    }

    private ModelAndView awardView(HistoryPagination awardPagination, HttpSession session) {
        if (awardPagination == null) {
            awardPagination = new HistoryPagination("Lịch sử thưởng", "/Award", "/history_award");
        }
        new HistoryAwardFacade().pageData(awardPagination);
        session.setAttribute("HISTORY_AWARD_PAGINATION", awardPagination);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + awardPagination.getViewName());
    }

    @RequestMapping(value = "/Award/ViewInsert", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getAwardViewInsert(HttpSession session) {
        HistoryPagination awardPagination = (HistoryPagination) session.getAttribute("HISTORY_AWARD_PAGINATION");
        if (awardPagination == null) {
            awardPagination = new HistoryPagination("Lịch sử thưởng", "/Award", "/history_award");
        }
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + awardPagination.getInsertViewName());
    }

    // Never up rank
    @RequestMapping(value = "/NeverUpRank", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDefaultNeverUpRankView(HttpSession session) {
        HistoryPagination neverUpRankPagination = (HistoryPagination) session.getAttribute("HISTORY_NEVER_UP_RANK_PAGINATION");
        if (neverUpRankPagination == null) {
            neverUpRankPagination = new HistoryPagination("Danh sách chưa từng nạp PV", "id", true, "/NeverUpRank", "/history_never_up_rank");
        }
        session.setAttribute("HISTORY_NEVER_UP_RANK_PAGINATION", neverUpRankPagination);
        return new ModelAndView(DefaultAdminPagination.CONTAINER_FOLDER + neverUpRankPagination.getViewName());
    }

    @RequestMapping(value = "/NeverUpRank/DisplayPerPage/{displayPerPage}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeDisplayPerPageForNeverUpRankView(@PathVariable("displayPerPage") int displayPerPage, HttpSession session) {
        HistoryPagination neverUpRankPagination = (HistoryPagination) session.getAttribute("HISTORY_NEVER_UP_RANK_PAGINATION");
        if (neverUpRankPagination != null) {
            neverUpRankPagination.setDisplayPerPage(displayPerPage);

        }
        return neverUpRankView(neverUpRankPagination, session);
    }

    @RequestMapping(value = "/NeverUpRank/OrderData/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView orderByNeverUpRankView(@PathVariable("orderBy") String orderBy, HttpSession session) {
        HistoryPagination neverUpRankPagination = (HistoryPagination) session.getAttribute("HISTORY_NEVER_UP_RANK_PAGINATION");
        if (neverUpRankPagination != null) {
            if (neverUpRankPagination.getOrderColmn().equals(orderBy)) {
                neverUpRankPagination.setAsc(!neverUpRankPagination.isAsc());
            }
            neverUpRankPagination.setOrderColmn(orderBy);
        }
        return neverUpRankView(neverUpRankPagination, session);
    }

    @RequestMapping(value = "/NeverUpRank/GoTo/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoNeverUpRankView(@PathVariable("page") int page, HttpSession session) {
        HistoryPagination neverUpRankPagination = (HistoryPagination) session.getAttribute("HISTORY_NEVER_UP_RANK_PAGINATION");
        if (neverUpRankPagination != null) {
            neverUpRankPagination.setCurrentPage(page);
        }
        return neverUpRankView(neverUpRankPagination, session);
    }

    @RequestMapping(value = "/NeverUpRank/ChangeAgency/{agencyId}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeAgencyViewInsert(@PathVariable("agencyId") Integer agencyId, HttpSession session) {
        HistoryPagination neverUpRankPagination = new HistoryPagination("Danh sách chưa từng nạp PV", "id", true, "/NeverUpRank", "/history_never_up_rank");
        neverUpRankPagination.setAgencyId(agencyId == -1 ? null : agencyId);
        return neverUpRankView(neverUpRankPagination, session);
    }

    @RequestMapping(value = "/NeverUpRank/Search", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public ModelAndView searchNeverUpRankView(@RequestBody Map map, HttpSession session) {
        String searchString = (String) map.get("searchString");
        if (StringUtils.isEmpty(searchString)) {
            return neverUpRankView(null, session);
        }
        List<String> keywords = (List) map.get("keywords");
        HistoryPagination neverUpRankPagination = new HistoryPagination("Danh sách chưa từng nạp PV", "id", true, "/NeverUpRank", "/history_never_up_rank");
        neverUpRankPagination.setSearchString(searchString);
        neverUpRankPagination.setKeywords(keywords);
        return neverUpRankView(neverUpRankPagination, session);
    }

    private ModelAndView neverUpRankView(HistoryPagination neverUpRankPagination, HttpSession session) {
        if (neverUpRankPagination == null) {
            neverUpRankPagination = new HistoryPagination("Danh sách chưa từng nạp PV", "id", true, "/NeverUpRank", "/history_never_up_rank");
        }
        Integer role = new AdminFacade().getAdminRoleByAdminId((Integer) session.getAttribute("ADMIN_ID"));
        Integer agencyId = 0;
        if (role == 2) {
            agencyId = new AdminFacade().getAdminAgencyByAdminId((Integer) session.getAttribute("ADMIN_ID"));
        }
        new CustomerFacade().pageDataNeverUpRank(neverUpRankPagination, role, agencyId);
        session.setAttribute("HISTORY_NEVER_UP_RANK_PAGINATION", neverUpRankPagination);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + neverUpRankPagination.getViewName());
    }

    @RequestMapping(value = "/NeverUpRank/ViewInsert/{userName}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getCustomerRankCustomerViewInsert1(@PathVariable(value = "userName") String userName, ModelMap mm, HttpSession session) {
        mm.put("USERNAME", userName);
        HistoryPagination neverUpRankPagination = (HistoryPagination) session.getAttribute("CUSTOMER_RANK_CUSTOMER_PAGINATION");
        if (neverUpRankPagination == null) {
            neverUpRankPagination = new HistoryPagination("Danh sách chưa từng nạp PV", "id", true, "/NeverUpRank", "/history_never_up_rank");
        }
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + neverUpRankPagination.getInsertViewName());
    }

    @RequestMapping(value = "/NeverUpRank/ViewInsertMultiple", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView viewInsertMultiple(@RequestBody Integer[] ids, ModelMap mm, HttpSession session) {
        session.setAttribute("LIST_ACTIVE_RANK", ids);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + "/history_active_rank_multiple_modal");
    }

    @RequestMapping(value = "/NeverUpRank/ActiveRank/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView activeRankCustomer(@PathVariable(value = "id") Integer id, ModelMap mm, HttpSession session) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        Integer role = new AdminFacade().getAdminRoleByAdminId((Integer) session.getAttribute("ADMIN_ID"));
        if (role == 3) {
            Customer c = (Customer) new CustomerFacade().find(id);
            if (c.getRankCustomerId() != null) {
                c.setIsAccountantApproved(true);
                try {
                    new CustomerFacade().edit(c);
                } catch (Exception ex) {
                    LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_DUYET, 4, "Duyệt yêu cầu kích hoạt: '" + c.getUserName() + "', Gói: '" + c.getRankCustomerId().getName() + "', lỗi");
                    mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Vui lòng thử lại sau!");
                    mm.put("MESSAGE_PAGINATION", mP);
                    return mAV;
                }
                LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_DUYET, 4, "Duyệt yêu cầu kích hoạt: '" + c.getUserName() + "', Gói: '" + c.getRankCustomerId().getName() + "', thành công");
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "Thành công", "Duyệt thành công!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
        }
        mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Vui lòng thử lại sau!");
        mm.put("MESSAGE_PAGINATION", mP);
        return mAV;
    }

    @RequestMapping(value = "/NeverUpRank/Deny/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView denyRankCustomer(@PathVariable(value = "id") Integer id, ModelMap mm, HttpSession session) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        Integer role = new AdminFacade().getAdminRoleByAdminId((Integer) session.getAttribute("ADMIN_ID"));
        if (role == 3 || role == 4) {
            Customer c = (Customer) new CustomerFacade().find(id);
            if (c.getRankCustomerId() != null) {
                c.setIsAdmin(true);
                if (role == 4) {
                    c.setIsAccountantApproved(false);
                }
                try {
                    new CustomerFacade().edit(c);
                } catch (Exception ex) {
                    LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_UPDATE, 4, "Báo lỗi yêu cầu kích hoạt: '" + c.getUserName() + "', Gói: '" + c.getRankCustomerId().getName() + "', lỗi");
                    mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Vui lòng thử lại sau!");
                    mm.put("MESSAGE_PAGINATION", mP);
                    return mAV;
                }
                LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_UPDATE, 4, "Báo lỗi yêu cầu kích hoạt: '" + c.getUserName() + "', Gói: '" + c.getRankCustomerId().getName() + "', thành công");
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "Thành công", "Báo lỗi thành công!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
        }
        mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Vui lòng thử lại sau!");
        mm.put("MESSAGE_PAGINATION", mP);
        return mAV;
    }

    @RequestMapping(value = "/NeverUpRank/ActiveRank", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView activeCustomer(@RequestBody Integer[] ids, ModelMap mm, HttpSession session) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        Integer result = 0;
        Integer role = new AdminFacade().getAdminRoleByAdminId((Integer) session.getAttribute("ADMIN_ID"));
        if (role == 3) {
            try {
                result = new CustomerFacade().acceptRankMultiple(ids);
            } catch (Exception ex) {
                ex.printStackTrace();
                LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_DUYET, 4, "Duyệt yêu cầu kích hoạt nhiều tài khoản trong danh sách ID: '" + new Gson().toJson(ids) + "', LỖI");
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Vui lòng thử lại sau!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
        }
        LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_DUYET, 4, "Duyệt yêu cầu nạp kích hoạt nhiều tài khoản trong danh sách ID: '" + new Gson().toJson(ids) + "', thành công: '" + result + "'");
        if (result > 0) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Duyệt yêu cầu kích hoạt " + ids.length + " tài khoản thành công!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        } else {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_WARNING, "Thông báo", "Không có yêu cầu nào được duyệt!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
    }

    @RequestMapping(value = "/NeverUpRank/InsertMultiple", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView insertMultiple(@RequestParam(value = "rankCustomerId") Integer rankCustomerId, ModelMap mm, HttpSession session) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        Integer result = 0;
        Integer role = new AdminFacade().getAdminRoleByAdminId((Integer) session.getAttribute("ADMIN_ID"));
        Integer[] ids = (Integer[]) session.getAttribute("LIST_ACTIVE_RANK");
        session.removeAttribute("LIST_ACTIVE_RANK");
        if (ids != null && role == 2 && ids.length > 0 && rankCustomerId != null) {
            try {
                result = new CustomerFacade().activeRankMultiple(ids, rankCustomerId);
            } catch (Exception ex) {
                ex.printStackTrace();
                LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_DUYET, 4, "Gửi yêu cầu kích hoạt nhiều tài khoản trong danh sách ID: '" + new Gson().toJson(ids) + "', gói: '" + rankCustomerId + "', Lỗi");
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Vui lòng thử lại sau!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
        }
        if (result == -1) {
            LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_DUYET, 4, "Gửi yêu cầu kích hoạt nhiều tài khoản trong danh sách ID: '" + new Gson().toJson(ids) + "', gói: '" + rankCustomerId + "', Lỗi: 'Không có yêu cầu nào được gửi đi!'");
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_WARNING, "Thông báo", "Không có yêu cầu nào được gửi đi!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        if (result == -2) {
            LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_DUYET, 4, "Gửi yêu cầu kích hoạt nhiều tài khoản trong danh sách ID: '" + new Gson().toJson(ids) + "', gói: '" + rankCustomerId + "', Lỗi:'Mã to nạp chậm!'");
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_WARNING, "Thông báo", "Mã to nạp chậm!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        if (result > 0) {
            LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_DUYET, 4, "Gửi yêu cầu kích hoạt nhiều tài khoản trong danh sách ID: '" + new Gson().toJson(ids) + "', gói: '" + rankCustomerId + "', thành công");
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Gửi yêu cầu kích hoạt " + ids.length + " tài khoản thành công!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        } else {
            LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_DUYET, 4, "Gửi yêu cầu kích hoạt nhiều tài khoản trong danh sách ID: '" + new Gson().toJson(ids) + "', gói: '" + rankCustomerId + "', Lỗi:'Không có yêu cầu nào được gửi đi!'");
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_WARNING, "Thông báo", "Không có yêu cầu nào được gửi đi!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
    }

    @RequestMapping(value = "/NeverUpRank/Insert/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView finishUpRankCustomer(@PathVariable(value = "id") Integer id, ModelMap mm, HttpSession session) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        Integer role = new AdminFacade().getAdminRoleByAdminId((Integer) session.getAttribute("ADMIN_ID"));
        Integer result = 0;
        if (role == 4) {
            Customer c = (Customer) new CustomerFacade().find(id);
            if (c.getRankCustomerId() != null && c.getIsAccountantApproved()) {
                try {
                    result = new CustomerRankCustomerFacade().depositPv(c.getUserName(), c.getRankCustomerId().getId(), 1, (Integer) session.getAttribute("ADMIN_ID"));
                } catch (Exception ex) {
                    mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Vui lòng thử lại sau!");
                    mm.put("MESSAGE_PAGINATION", mP);
                    return mAV;
                }
            }
        }
        switch (result) {
            case 1: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Tài khoản đã từng nạp gói PV này!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 2: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Nạp PV thành công!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 3: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Yêu cầu nạp gói 300PV trước!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            default: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Vui lòng thử lại sau!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
        }
    }

    @RequestMapping(value = "/NeverUpRank/Insert", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView insertNeverUpRankCustomer(@RequestParam(value = "userName") String userName,
            @RequestParam(value = "rankCustomerId") Integer rankCustomerId,
            @RequestParam(value = "multipleGrateful", required = false) Integer multipleGrateful,
            ModelMap mm, HttpSession session) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        Integer role = new AdminFacade().getAdminRoleByAdminId((Integer) session.getAttribute("ADMIN_ID"));
        Customer c = new CustomerFacade().findCustomerByUsername(userName);
        try {
            if (role == 2) {
                if (rankCustomerId > 3) {
                    Integer check = new CustomerFacade().checkAgencyOK(c.getId(), rankCustomerId);
                    if (check == 0) {
                        LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_YEUCAU_NAPTIEN, 4, "Gửi yêu cầu kích hoạt tài khoản: '" + c.getUserName() + "', gói: '" + rankCustomerId + "', lỗi: 'Hệ thống chưa đủ thành viên hoặc đã có người chọn mã!'");
                        mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Hệ thống chưa đủ thành viên hoặc đã có người chọn mã!");
                        mm.put("MESSAGE_PAGINATION", mP);
                        return mAV;
                    }
                }
                c.setRankCustomerId(new RankCustomes(rankCustomerId));
                c.setAgencyApprovedDate(new Date());
                new CustomerFacade().edit(c);
                LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_YEUCAU_NAPTIEN, 4, "Gửi yêu cầu kích hoạt tài khoản: '" + c.getUserName() + "', gói: '" + rankCustomerId + "', thành công: 'Có'");
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Kích hoạt thành công! Chờ kế toán duyệt!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_YEUCAU_NAPTIEN, 4, "Gửi yêu cầu kích hoạt tài khoản: '" + c.getUserName() + "', gói: '" + rankCustomerId + "', thành công: 'Không'");
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Vui lòng thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_YEUCAU_NAPTIEN, 4, "Gửi yêu cầu kích hoạt tài khoản: '" + c.getUserName() + "', gói: '" + rankCustomerId + "', thành công: 'Không'");
        mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Vui lòng thử lại sau!");
        mm.put("MESSAGE_PAGINATION", mP);
        return mAV;
    }

    // Rank Customer
    @RequestMapping(value = "/RankCustomer", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDefaultRankCustomerView(ModelMap mm, HttpSession session) {
        HistoryPagination rankCustomerPagniation = new HistoryPagination("Danh sách gói PV", "price", true, "/RankCustomer", "/history_rank_customer");
        session.setAttribute("RANK_CUSTOMER_PAGINATION", rankCustomerPagniation);
        return new ModelAndView(DefaultAdminPagination.CONTAINER_FOLDER + rankCustomerPagniation.getViewName());
    }

    @RequestMapping(value = "/RankCustomer/DisplayPerPage/{displayPerPage}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeDisplayPerPageForRankCustomerView(@PathVariable("displayPerPage") int displayPerPage, ModelMap mm, HttpSession session) {
        HistoryPagination rankCustomerPagniation = (HistoryPagination) session.getAttribute("RANK_CUSTOMER_PAGINATION");
        if (rankCustomerPagniation != null) {
            rankCustomerPagniation.setDisplayPerPage(displayPerPage);

        }
        return rankCustomerView(rankCustomerPagniation, session);
    }

    @RequestMapping(value = "/RankCustomer/OrderData/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView orderByRankCustomerView(@PathVariable("orderBy") String orderBy, ModelMap mm, HttpSession session) {
        HistoryPagination rankCustomerPagniation = (HistoryPagination) session.getAttribute("RANK_CUSTOMER_PAGINATION");
        if (rankCustomerPagniation != null) {
            if (rankCustomerPagniation.getOrderColmn().equals(orderBy)) {
                rankCustomerPagniation.setAsc(!rankCustomerPagniation.isAsc());
            }
            rankCustomerPagniation.setOrderColmn(orderBy);
        }
        return rankCustomerView(rankCustomerPagniation, session);
    }

    @RequestMapping(value = "/RankCustomer/GoTo/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoRankCustomerView(@PathVariable("page") int page, ModelMap mm, HttpSession session) {
        HistoryPagination rankCustomerPagniation = (HistoryPagination) session.getAttribute("RANK_CUSTOMER_PAGINATION");
        if (rankCustomerPagniation != null) {
            rankCustomerPagniation.setCurrentPage(page);
        }
        return rankCustomerView(rankCustomerPagniation, session);
    }

    @RequestMapping(value = "/RankCustomer/Search", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public ModelAndView searchRankCustomerView(@RequestBody Map map, HttpSession session) {
        String searchString = (String) map.get("searchString");
        if (StringUtils.isEmpty(searchString)) {
            return rankCustomerView(null, session);
        }
        List<String> keywords = (List) map.get("keywords");
        HistoryPagination rankCustomerPagniation = new HistoryPagination("Danh sách gói PV", "price", true, "/RankCustomer", "/history_rank_customer");
        rankCustomerPagniation.setSearchString(searchString);
        rankCustomerPagniation.setKeywords(keywords);
        return rankCustomerView(rankCustomerPagniation, session);
    }

    private ModelAndView rankCustomerView(HistoryPagination rankCustomerPagniation, HttpSession session) {
        if (rankCustomerPagniation == null) {
            rankCustomerPagniation = new HistoryPagination("Danh sách gói PV", "price", true, "/RankCustomer", "/history_rank_customer");
        }
        new RankCustomersFacade().pageData(rankCustomerPagniation);
        session.setAttribute("RANK_CUSTOMER_PAGINATION", rankCustomerPagniation);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + rankCustomerPagniation.getViewName());
    }

    @RequestMapping(value = "/RankCustomer/ViewInsert", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getRankCustomerViewInsert(HttpSession session) {
        HistoryPagination rankCustomerPagniation = (HistoryPagination) session.getAttribute("RANK_CUSTOMER_PAGINATION");
        if (rankCustomerPagniation == null) {
            rankCustomerPagniation = new HistoryPagination("Danh sách gói PV", "id", false, "/RankCustomer", "/history_rank_customer");
        }
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + rankCustomerPagniation.getInsertViewName());
    }

    //CheckAward
    @RequestMapping(value = "/CheckAward", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDefaultCheckAwardView(ModelMap mm, HttpSession session) {
        HistoryPagination checkAwardPagniation = new HistoryPagination("Danh sách các gói thưởng", "id", true, "/CheckAward", "/history_check_award");
        session.setAttribute("CHECK_AWARD_PAGINATION", checkAwardPagniation);
        return new ModelAndView(DefaultAdminPagination.CONTAINER_FOLDER + checkAwardPagniation.getViewName());
    }

    @RequestMapping(value = "/CheckAward/DisplayPerPage/{displayPerPage}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeDisplayPerPageForCheckAwardView(@PathVariable("displayPerPage") int displayPerPage, ModelMap mm, HttpSession session) {
        HistoryPagination checkAwardPagniation = (HistoryPagination) session.getAttribute("CHECK_AWARD_PAGINATION");
        if (checkAwardPagniation != null) {
            checkAwardPagniation.setDisplayPerPage(displayPerPage);

        }
        return checkAwardView(checkAwardPagniation, session);
    }

    @RequestMapping(value = "/CheckAward/OrderData/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView orderByCheckAwardView(@PathVariable("orderBy") String orderBy, ModelMap mm, HttpSession session) {
        HistoryPagination checkAwardPagniation = (HistoryPagination) session.getAttribute("CHECK_AWARD_PAGINATION");
        if (checkAwardPagniation != null) {
            if (checkAwardPagniation.getOrderColmn().equals(orderBy)) {
                checkAwardPagniation.setAsc(!checkAwardPagniation.isAsc());
            }
            checkAwardPagniation.setOrderColmn(orderBy);
        }
        return checkAwardView(checkAwardPagniation, session);
    }

    @RequestMapping(value = "/CheckAward/GoTo/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoCheckAwardView(@PathVariable("page") int page, ModelMap mm, HttpSession session) {
        HistoryPagination checkAwardPagniation = (HistoryPagination) session.getAttribute("CHECK_AWARD_PAGINATION");
        if (checkAwardPagniation != null) {
            checkAwardPagniation.setCurrentPage(page);
        }
        return checkAwardView(checkAwardPagniation, session);
    }

    @RequestMapping(value = "/CheckAward/Search", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public ModelAndView searchCheckAwardView(@RequestBody Map map, HttpSession session) {
        String searchString = (String) map.get("searchString");
        if (StringUtils.isEmpty(searchString)) {
            return checkAwardView(null, session);
        }
        List<String> keywords = (List) map.get("keywords");
        HistoryPagination checkAwardPagniation = new HistoryPagination("Danh sách các gói thưởng", "id", true, "/CheckAward", "/history_check_award");
        checkAwardPagniation.setSearchString(searchString);
        checkAwardPagniation.setKeywords(keywords);
        return checkAwardView(checkAwardPagniation, session);
    }

    private ModelAndView checkAwardView(HistoryPagination checkAwardPagniation, HttpSession session) {
        if (checkAwardPagniation == null) {
            checkAwardPagniation = new HistoryPagination("Danh sách các gói thưởng", "id", true, "/CheckAward", "/history_check_award");
        }
        new CheckAwardsFacade().pageData(checkAwardPagniation);
        session.setAttribute("CHECK_AWARD_PAGINATION", checkAwardPagniation);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + checkAwardPagniation.getViewName());
    }

    //WaitActiveRank   
    @RequestMapping(value = "/WaitActiveRank", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDefaultWaitActiveRankView(ModelMap mm, HttpSession session) {
        HistoryPagination customerRankCustomerPagination = (HistoryPagination) session.getAttribute("WAIT_ACTIVE_RANK_PAGINATION");
        if (customerRankCustomerPagination == null) {
            customerRankCustomerPagination = new HistoryPagination("Danh sách chờ duyệt", "/WaitActiveRank", "/history_wait_active_rank");
            customerRankCustomerPagination.setAsc(true);
            customerRankCustomerPagination.setOrderColmn("id");
        }
        session.setAttribute("WAIT_ACTIVE_RANK_PAGINATION", customerRankCustomerPagination);
        return new ModelAndView(DefaultAdminPagination.CONTAINER_FOLDER + customerRankCustomerPagination.getViewName());
    }

    @RequestMapping(value = "/WaitActiveRank/DisplayPerPage/{displayPerPage}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeDisplayPerPageForWaitActiveRankView(@PathVariable("displayPerPage") int displayPerPage, ModelMap mm, HttpSession session) {
        HistoryPagination customerRankCustomerPagination = (HistoryPagination) session.getAttribute("WAIT_ACTIVE_RANK_PAGINATION");
        if (customerRankCustomerPagination != null) {
            customerRankCustomerPagination.setDisplayPerPage(displayPerPage);
        }
        return waitActiveRankView(customerRankCustomerPagination, session);
    }

    @RequestMapping(value = "/WaitActiveRank/OrderData/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView orderByWaitActiveRankView(@PathVariable("orderBy") String orderBy, ModelMap mm, HttpSession session) {
        HistoryPagination customerRankCustomerPagination = (HistoryPagination) session.getAttribute("WAIT_ACTIVE_RANK_PAGINATION");
        if (customerRankCustomerPagination != null) {
            if (customerRankCustomerPagination.getOrderColmn().equals(orderBy)) {
                customerRankCustomerPagination.setAsc(!customerRankCustomerPagination.isAsc());
            }
            customerRankCustomerPagination.setOrderColmn(orderBy);
        }
        return waitActiveRankView(customerRankCustomerPagination, session);
    }

    @RequestMapping(value = "/WaitActiveRank/GoTo/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoWaitActiveRankView(@PathVariable("page") int page, ModelMap mm, HttpSession session) {
        HistoryPagination customerRankCustomerPagination = (HistoryPagination) session.getAttribute("WAIT_ACTIVE_RANK_PAGINATION");
        if (customerRankCustomerPagination != null) {
            customerRankCustomerPagination.setCurrentPage(page);
        }
        return waitActiveRankView(customerRankCustomerPagination, session);
    }

    @RequestMapping(value = "/WaitActiveRank/Search", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public ModelAndView searchWaitActiveRankView(@RequestBody Map map, HttpSession session) {
        String searchString = (String) map.get("searchString");
        if (StringUtils.isEmpty(searchString)) {
            return waitActiveRankView(null, session);
        }
        List<String> keywords = (List) map.get("keywords");
        HistoryPagination customerRankCustomerPagination = new HistoryPagination("Danh sách chờ duyệt", "/WaitActiveRank", "/history_wait_active_rank");
        customerRankCustomerPagination.setAsc(true);
        customerRankCustomerPagination.setOrderColmn("id");
        customerRankCustomerPagination.setSearchString(searchString);
        customerRankCustomerPagination.setKeywords(keywords);
        return waitActiveRankView(customerRankCustomerPagination, session);
    }

    @RequestMapping(value = "/WaitActiveRank/ChangeStatus/{status}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView searchWaitActiveRankView(@PathVariable(value = "status") Integer status, HttpSession session) {
        HistoryPagination customerRankCustomerPagination = new HistoryPagination("Danh sách chờ duyệt", "/WaitActiveRank", "/history_wait_active_rank");
        if (status == -1) {
            customerRankCustomerPagination.setAccepted(null);
        } else if (status == 0) {
            customerRankCustomerPagination.setAccepted(false);
        }
        if (status == 1) {
            customerRankCustomerPagination.setAccepted(true);
        }
        customerRankCustomerPagination.setAsc(true);
        customerRankCustomerPagination.setOrderColmn("id");
        return waitActiveRankView(customerRankCustomerPagination, session);
    }

    private ModelAndView waitActiveRankView(HistoryPagination customerRankCustomerPagination, HttpSession session) {
        if (customerRankCustomerPagination == null) {
            customerRankCustomerPagination = new HistoryPagination("Danh sách chờ duyệt", "/WaitActiveRank", "/history_wait_active_rank");
            customerRankCustomerPagination.setAsc(true);
            customerRankCustomerPagination.setOrderColmn("id");
        }
        Integer agencyId = new AdminFacade().getAdminAgencyByAdminId((Integer) session.getAttribute("ADMIN_ID"));
        new CustomerRankCustomerFacade().pageDataWaitActiveRank(customerRankCustomerPagination, agencyId);
        session.setAttribute("WAIT_ACTIVE_RANK_PAGINATION", customerRankCustomerPagination);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + customerRankCustomerPagination.getViewName());
    }

    @RequestMapping(value = "/WaitActiveRank/ViewEdit/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getCustomerRankCustomerViewEdit(@PathVariable(value = "id") Integer id, ModelMap mm, HttpSession session) {
        Customer c = (Customer) new CustomerFacade().find(id);
        mm.put("CUSTOMER_EDIT", c);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + "/history_deposit_pv_edit_modal");
    }

    @RequestMapping(value = "/WaitActiveRank/Edit", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView getCustomerRankCustomerViewEdit(@RequestParam(value = "id") Integer id, @RequestParam(value = "rankCustomerId") Integer rankCustomerId, ModelMap mm, HttpSession session) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        Integer role = new AdminFacade().getAdminRoleByAdminId((Integer) session.getAttribute("ADMIN_ID"));
        Integer agency = new AdminFacade().getAdminAgencyByAdminId((Integer) session.getAttribute("ADMIN_ID"));
        if (role == 2) {
            Customer c = (Customer) new CustomerFacade().find(id);
            if (agency == c.getProvincialAgencies().getId() || agency == 0) {
                c.setRankCustomerId(new RankCustomes(rankCustomerId));
                c.setIsAdmin(false);
                try {
                    new CustomerFacade().edit(c);
                } catch (Exception e) {
                    LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_UPDATE, 4, "Sửa yêu cầu kích hoạt tài khoản: '" + c.getUserName() + "', từ gói: '" + c.getRankCustomerId().getId() + "', sang gói:'" + rankCustomerId + "', lỗi");
                    mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Vui lòng thử lại sau!");
                    mm.put("MESSAGE_PAGINATION", mP);
                    return mAV;
                }
                LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_UPDATE, 4, "Sửa yêu cầu kích hoạt tài khoản: '" + c.getUserName() + "', từ gói: '" + c.getRankCustomerId().getId() + "', sang gói:'" + rankCustomerId + "', thành công");
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "Thành công", "Cập nhật thành công!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
        }
        mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Vui lòng thử lại sau!");
        mm.put("MESSAGE_PAGINATION", mP);
        return mAV;
    }
}
