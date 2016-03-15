package com.resources.controller;

import com.resources.facade.AdminFacade;
import com.resources.entity.Admin;
import com.resources.function.CustomFunction;
import com.resources.pagination.admin.DefaultAdminPagination;
import com.resources.pagination.admin.MessagePagination;
import com.resources.utils.LeoConstants;
import com.resources.utils.LogUtils;
import com.resources.utils.StringUtils;
import java.util.Map;
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
@RequestMapping(value = "/Admin")
public class AdminController {

    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public ModelAndView getHomeView(ModelMap mm) {
//        mm.put("HISTORY_AWARD_MAP", new HistoryAwardFacade().reportAllAwardByMonth(Calendar.getInstance().get(Calendar.YEAR)));
//        mm.put("HISTORY_COMISSION_MAP", new HistoryAwardFacade().reportAllComissionByMonth(Calendar.getInstance().get(Calendar.YEAR)));
//        mm.put("HISTORY_TOTAL_AWARD_YEAR", new HistoryAwardFacade().reportAllTotalAwardInCurrentYear());
//        mm.put("TOTAL_IN_CURRENT_MONTH", new HistoryAwardFacade().getTotalInInCurrentMonth());
//        mm.put("TOTAL_IN_CURRENT_YEAR", new HistoryAwardFacade().getTotalInInCurrentYear());
//        mm.put("TOTAL_OUT_CURRENT_MONTH", new HistoryAwardFacade().getTotalOutInCurrentMonth());
//        mm.put("TOTAL_OUT_CURRENT_YEAR", new HistoryAwardFacade().getTotalOutInCurrentYear());
//        mm.put("TOTAL_USER_CURRENT_MONTH", new HistoryAwardFacade().countNewUserInCurrentMonth());
//        mm.put("NEWEST_DEPOSIT", new CustomerRankCustomerFacade().getNewestDeposit());
//        mm.put("NEWEST_AWARD", new CustomerRankCustomerFacade().getNewestAward());
//        mm.put("NEWEST_USER", new CustomerFacade().getNewestUser());
//        mm.put("TOP_5_DEPOSIT_MONTH", new CustomerRankCustomerFacade().getTop5DepositPVInMonth());
//        mm.put("TOP_5_DEPOSIT_YEAR", new CustomerRankCustomerFacade().getTop5DepositPVInYear());
//        mm.put("TOP_5_AWARD_MONTH", new HistoryAwardFacade().getTop5AwardInMonth());
//        mm.put("TOP_5_AWARD_YEAR", new HistoryAwardFacade().getTop5AwardInYear());
        return new ModelAndView("admin");
    }

    @RequestMapping(value = "/Login", method = RequestMethod.GET)
    public ModelAndView getLoginView(ModelMap mm) {
        return new ModelAndView("includes/admin/login");
    }

    @RequestMapping(value = "/Login", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView login(@RequestBody Map map, ModelMap mm, HttpServletRequest request) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mp;
        Admin admin=new Admin();
        admin.setUserName(String.valueOf(map.get("userName")));
        admin.setPassword(String.valueOf(map.get("password")));
        String checkCaptcha = String.valueOf(map.get("captcha"));
        if (StringUtils.isEmpty(admin.getUserName()) || StringUtils.isEmpty(admin.getPassword())||StringUtils.isEmpty(checkCaptcha)) {
            mp = new MessagePagination(MessagePagination.MESSAGE_TYPE_WARNING, "Chú ý", "Vui lòng điền tất cả các ô!");
            mm.put("MESSAGE_PAGINATION", mp);
            return mAV;
        }
        Map captchaList = (Map) request.getSession().getAttribute("CAPTCHA");
        String triAnCaptcha = (String) captchaList.get("ADMIN_LOGIN");
        checkCaptcha=checkCaptcha.toLowerCase();
        if (checkCaptcha == null || !CustomFunction.md5(checkCaptcha).equals(triAnCaptcha)) {
            mp = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Mã captcha không hợp lệ!");
            mm.put("MESSAGE_PAGINATION", mp);
            return mAV;
        }
        admin.setPassword(CustomFunction.md5(admin.getPassword()));
        try {
            admin = new AdminFacade().login(admin);
            if (admin == null || admin.getIsDelete()) {
                mp = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Sai tên đăng nhập hoặc mật khẩu!");
                mm.put("MESSAGE_PAGINATION", mp);
                return mAV;
            }
            if (!admin.getIsActive()) {
                mp = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Cảnh bảo", "Tài khoản của bạn đã bị khóa!");
                mm.put("MESSAGE_PAGINATION", mp);
                return mAV;
            }
            request.getSession().setAttribute("ADMIN_ID", admin.getId());
            mm.put("REDIRECT_URL", "/Admin");
            mAV = new ModelAndView(DefaultAdminPagination.REDIRECT_FOLDER + DefaultAdminPagination.REDIRECT_VIEW);
            return mAV;
        } catch (Exception e) {
            mp = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mp);
            return mAV;
        }
    }
    @RequestMapping(value = "/ViewEdit/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getListAdminViewInsert(@PathVariable(value = "id") Integer id, ModelMap mm, HttpSession session) {
        Admin admin = (Admin) new AdminFacade().find(id);
        mm.put("ADMIN_EDIT", admin);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + "/permission_admin_edit_modal");
    }
    
    @RequestMapping(value = "/Edit", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView edit(@RequestBody Map admin, ModelMap mm, HttpSession session) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        int result;
        Integer role=new AdminFacade().getAdminRoleByAdminId((Integer) session.getAttribute("ADMIN_ID"));
        try {
            result = new AdminFacade().edit(admin, role);
        } catch (Exception e) {
            LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_ADD, 9, "Cập nhật quản trị viên id:'" + Integer.parseInt(admin.get("id").toString()) + ", Lỗi");
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        switch (result) {
            case 1: {
                LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_ADD, 9, "Cập nhật quản trị viên id:'" + Integer.parseInt(admin.get("id").toString()) + ", Lỗi");
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Thử lại sau!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 2: {
                LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_ADD, 9, "Cập nhật quản trị viên id:'" + Integer.parseInt(admin.get("id").toString()) + ", Lỗi");
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_WARNING, "Chú ý", "Yêu cầu nhập tất cả các thông tin được yêu cầu!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 3: {
                LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_ADD, 9, "Cập nhật quản trị viên id:'" + Integer.parseInt(admin.get("id").toString()) + ", Thành công");
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Cập nhật thành công!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            default: {
                LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_ADD, 9, "Cập nhật quản trị viên id:'" + Integer.parseInt(admin.get("id").toString()) + ", Lỗi");
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Thử lại sau!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
        }
    }

    @RequestMapping(value = "/Logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return new ModelAndView("includes/admin/login");
    }
}
