package com.resources.controller;

import com.resources.facade.CustomerRankCustomerFacade;
import com.resources.facade.PinSysFacade;
import com.resources.pagination.index.*;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/History")
public class IndexHistoryController {

    //CustomerRankCustomer    

    @RequestMapping(value = "/CustomerRankCustomer", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDefaultCustomerRankCustomerView(ModelMap mm, HttpSession session) {
        HistoryPagination customerRankCustomerPagination = new HistoryPagination("Lịch sử nạp PV", "/CustomerRankCustomer", "/history_customer_rank_customer");
        session.setAttribute("INDEX_CUSTOMER_RANK_CUSTOMER_PAGINATION", customerRankCustomerPagination);
        return new ModelAndView(DefaultIndexPagination.CONTAINER_FOLDER + customerRankCustomerPagination.getViewName());
    }

    @RequestMapping(value = "/CustomerRankCustomer/DisplayPerPage/{displayPerPage}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeDisplayPerPageForCustomerRankCustomerView(@PathVariable("displayPerPage") int displayPerPage, ModelMap mm, HttpSession session) {
        HistoryPagination customerRankCustomerPagination = (HistoryPagination) session.getAttribute("INDEX_CUSTOMER_RANK_CUSTOMER_PAGINATION");
        if (customerRankCustomerPagination != null) {
            customerRankCustomerPagination.setDisplayPerPage(displayPerPage);

        }
        return customerRankCustomerView(customerRankCustomerPagination,session);
    }

    @RequestMapping(value = "/CustomerRankCustomer/OrderData/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView orderByCustomerRankCustomerView(@PathVariable("orderBy") String orderBy, ModelMap mm, HttpSession session) {
        HistoryPagination customerRankCustomerPagination = (HistoryPagination) session.getAttribute("INDEX_CUSTOMER_RANK_CUSTOMER_PAGINATION");
        if (customerRankCustomerPagination != null) {
            if (customerRankCustomerPagination.getOrderColmn().equals(orderBy)) {
                customerRankCustomerPagination.setAsc(!customerRankCustomerPagination.isAsc());
            }
            customerRankCustomerPagination.setOrderColmn(orderBy);
        }
        return customerRankCustomerView(customerRankCustomerPagination,session);
    }

    @RequestMapping(value = "/CustomerRankCustomer/GoTo/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoCustomerRankCustomerView(@PathVariable("page") int page, ModelMap mm, HttpSession session) {
        HistoryPagination customerRankCustomerPagination = (HistoryPagination) session.getAttribute("INDEX_CUSTOMER_RANK_CUSTOMER_PAGINATION");
        if (customerRankCustomerPagination != null) {
            customerRankCustomerPagination.setCurrentPage(page);
        }
        return customerRankCustomerView(customerRankCustomerPagination,session);
    }

    private ModelAndView customerRankCustomerView(HistoryPagination customerRankCustomerPagination, HttpSession session) {
        if (customerRankCustomerPagination == null) {
            customerRankCustomerPagination = new HistoryPagination("Lịch sử nạp PV", "/CustomerRankCustomer", "/history_customer_rank_customer");
        }
        new CustomerRankCustomerFacade().pageData(customerRankCustomerPagination, (Integer) session.getAttribute("CUSTOMER_ID"));
        session.setAttribute("INDEX_CUSTOMER_RANK_CUSTOMER_PAGINATION", customerRankCustomerPagination);
        return new ModelAndView(DefaultIndexPagination.AJAX_FOLDER + customerRankCustomerPagination.getViewName());
    }

    @RequestMapping(value = {"/CustomerRankCustomer/ViewInsert"}, method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getViewInsert(HttpSession session) {
        HistoryPagination customerRankCustomerPagination = (HistoryPagination) session.getAttribute("INDEX_CUSTOMER_RANK_CUSTOMER_PAGINATION");
        if (customerRankCustomerPagination == null) {
            customerRankCustomerPagination = new HistoryPagination("Lịch sử nạp PV", "/CustomerRankCustomer", "/history_customer_rank_customer");
        }
        session.setAttribute("INDEX_CUSTOMER_RANK_CUSTOMER_PAGINATION", customerRankCustomerPagination);
        return new ModelAndView(DefaultIndexPagination.CONTAINER_FOLDER + customerRankCustomerPagination.getInsertViewName());
    }

    @RequestMapping(value = {"/CustomerRankCustomer/DepositPV"}, method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView depositPv(HttpSession session) {
        HistoryPagination customerRankCustomerPagination = (HistoryPagination) session.getAttribute("INDEX_CUSTOMER_RANK_CUSTOMER_PAGINATION");
        if (customerRankCustomerPagination == null) {
            customerRankCustomerPagination = new HistoryPagination("Lịch sử nạp PV", "/CustomerRankCustomer", "/history_customer_rank_customer");
        }
        session.setAttribute("INDEX_CUSTOMER_RANK_CUSTOMER_PAGINATION", customerRankCustomerPagination);
        return new ModelAndView(DefaultIndexPagination.CONTAINER_FOLDER + customerRankCustomerPagination.getInsertViewName());
    }

    //History used pinsys
    @RequestMapping(value = "/UsedPinSys", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDefaultUsedPinSysView(ModelMap mm,HttpSession session) {
        HistoryPagination pinsysPagination = new HistoryPagination("Lịch sử nạp thẻ PIN", "usedDate", false, "/UsedPinSys", "/history_used_pin_sys");
        session.setAttribute("INDEX_USED_PIN_SYS_PAGINATION", pinsysPagination);
        return new ModelAndView(DefaultIndexPagination.CONTAINER_FOLDER + pinsysPagination.getViewName());
    }

    @RequestMapping(value = "/UsedPinSys/DisplayPerPage/{displayPerPage}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeDisplayPerPageForUsedPinSysView(@PathVariable("displayPerPage") int displayPerPage, ModelMap mm,HttpSession session) {
        HistoryPagination pinsysPagination = (HistoryPagination) session.getAttribute("INDEX_USED_PIN_SYS_PAGINATION");
        if (pinsysPagination != null) {
            pinsysPagination.setDisplayPerPage(displayPerPage);
        }
        return usedPinSysView(pinsysPagination,session);
    }

    @RequestMapping(value = "/UsedPinSys/OrderData/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView orderByUsedPinSysView(@PathVariable("orderBy") String orderBy, ModelMap mm,HttpSession session) {
        HistoryPagination pinsysPagination = (HistoryPagination) session.getAttribute("INDEX_USED_PIN_SYS_PAGINATION");
        if (pinsysPagination != null) {
            if (pinsysPagination.getOrderColmn().equals(orderBy)) {
                pinsysPagination.setAsc(!pinsysPagination.isAsc());
            }
            pinsysPagination.setOrderColmn(orderBy);
        }
        return usedPinSysView(pinsysPagination,session);
    }

    @RequestMapping(value = "/UsedPinSys/GoTo/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoUsedPinSysView(@PathVariable("page") int page, ModelMap mm,HttpSession session) {
        HistoryPagination pinsysPagination = (HistoryPagination) session.getAttribute("INDEX_USED_PIN_SYS_PAGINATION");
        if (pinsysPagination != null) {
            pinsysPagination.setCurrentPage(page);
        }
        return usedPinSysView(pinsysPagination,session);
    }

    private ModelAndView usedPinSysView(HistoryPagination pinsysPagination,HttpSession session) {
        if (pinsysPagination == null) {
            pinsysPagination = new HistoryPagination("Lịch sử nạp thẻ PIN", "usedDate", false, "/UsedPinSys", "/history_used_pin_sys");
        }
        new PinSysFacade().pageDataUsedPinSys(pinsysPagination, (Integer) session.getAttribute("CUSTOMER_ID"));
        session.setAttribute("INDEX_USED_PIN_SYS_PAGINATION", pinsysPagination);
        return new ModelAndView(DefaultIndexPagination.AJAX_FOLDER + pinsysPagination.getViewName());
    }
}
