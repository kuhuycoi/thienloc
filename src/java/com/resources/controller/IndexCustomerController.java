package com.resources.controller;

import com.resources.facade.CustomerFacade;
import com.resources.bean.CustomerNonActive;
import com.resources.bean.CustomerTree;
import com.resources.entity.Customer;
import com.resources.entity.ProvincialAgencies;
import com.resources.function.CustomFunction;
import com.resources.pagination.index.*;
import com.resources.utils.ConfigUtils;
import com.resources.utils.StringUtils;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
@RequestMapping(value = "/Customer")
public class IndexCustomerController {

    @RequestMapping(value = "/ResetPassword", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getResetPasswordView(ModelMap mm) {
        return new ModelAndView(DefaultIndexPagination.AJAX_FOLDER + DefaultIndexPagination.RESET_PASSWORD_VIEW);
    }

//    @RequestMapping(value = "/ResetPassword", method = RequestMethod.POST)
//    @ResponseBody
//    public ModelAndView resetPasswordView(@RequestBody Customer cus, ModelMap mm, HttpServletRequest request) {
//        ModelAndView mAV = new ModelAndView(MessagePagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
//        MessagePagination messagePagination;
//        if (StringUtils.isEmpty(cus.getUserName()) || StringUtils.isEmpty(cus.getEmail())) {
//            messagePagination = new MessagePagination(MessagePagination.MESSAGE_TYPE_WARNING, "Chú ý", "Vui lòng điền tên đăng nhập và email!");
//            mm.put("MESSAGE_PAGINATION", messagePagination);
//            return mAV;
//        }
//        try {
//            Integer rs = 0;
//            switch (rs) {
//                case 1: {
//                    messagePagination = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Chú ý", "Tên đăng nhập hoặc email không hợp lệ!");
//                    mm.put("MESSAGE_PAGINATION", messagePagination);
//                    return mAV;
//                }
//                case 2: {
//                    messagePagination = new MessagePagination(MessagePagination.MESSAGE_TYPE_WARNING, "Chú ý", "Mật khẩu đã được reset để đảm bảo an toàn cho tài khoản của bạn nhưng chưa được gửi về email!");
//                    mm.put("MESSAGE_PAGINATION", messagePagination);
//                    return mAV;
//                }
//                case 3: {
//                    messagePagination = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "Thành công", "Mật khẩu đã được reset và gửi về hòm thư của bạn!");
//                    mm.put("MESSAGE_PAGINATION", messagePagination);
//                    return mAV;
//                }
//            }
//            request.getSession(false).setAttribute("CUSTOMER_ID", cus.getId());
//            mm.put("REDIRECT_URL", "/Home");
//            mAV = new ModelAndView(DefaultIndexPagination.REDIRECT_FOLDER + DefaultIndexPagination.REDIRECT_VIEW);
//            return mAV;
//        } catch (Exception e) {
//            e.printStackTrace();
//            messagePagination = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Thử lại sau!");
//            mm.put("MESSAGE_PAGINATION", messagePagination);
//            return mAV;
//        }
//    }
    @RequestMapping(value = "/ChangePassword", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getChangePasswordView(ModelMap mm) {
        return new ModelAndView(DefaultIndexPagination.AJAX_FOLDER + DefaultIndexPagination.CHANGE_PASSWORD_VIEW);
    }

    @RequestMapping(value = "/ChangePassword", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView changePassword(@RequestBody Map map, ModelMap mm, HttpSession session) {
        ModelAndView mAV = new ModelAndView(MessagePagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination messagePagination;
        String oldPassword = (String) map.get("oldPassword");
        String newPassword = (String) map.get("newPassword");
        if (StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(newPassword)) {
            messagePagination = new MessagePagination(MessagePagination.MESSAGE_TYPE_WARNING, "Chú ý", "Vui lòng điền đầy đủ thông tin!");
            mm.put("MESSAGE_PAGINATION", messagePagination);
            return mAV;
        }
        oldPassword = CustomFunction.md5(oldPassword);
        newPassword = CustomFunction.md5(newPassword);
        Integer cusid = (Integer) session.getAttribute("CUSTOMER_ID");
        try {
            Customer cus = (Customer) new CustomerFacade().find(cusid);
            if (!oldPassword.equals(cus.getPassword())) {
                messagePagination = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Mật khẩu không chính xác!");
                mm.put("MESSAGE_PAGINATION", messagePagination);
                return mAV;
            }
            if (oldPassword.equals(newPassword)) {
                messagePagination = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "Thành công", "Cập nhật mật khẩu thành công!");
                mm.put("MESSAGE_PAGINATION", messagePagination);
                return mAV;
            }
            cus.setPassword(newPassword);
            new CustomerFacade().edit(cus);
            messagePagination = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "Thành công", "Cập nhật mật khẩu thành công!");
            mm.put("MESSAGE_PAGINATION", messagePagination);
            return mAV;
        } catch (Exception ex) {
            ex.printStackTrace();
            messagePagination = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Thử lại sau!");
            mm.put("MESSAGE_PAGINATION", messagePagination);
            return mAV;
        }
    }

    @RequestMapping(value = "/Login", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView login(@RequestBody Map map, ModelMap mm, HttpServletRequest request) {
        ModelAndView mAV = new ModelAndView(MessagePagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        Customer cus = new Customer();
        cus.setUserName(String.valueOf(map.get("userName")));
        cus.setPassword(String.valueOf(map.get("password")));
//        String checkCaptcha = String.valueOf(map.get("captcha"));
        if (StringUtils.isEmpty(cus.getUserName()) || StringUtils.isEmpty(cus.getPassword())) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_WARNING, "Chú ý", "Vui lòng điền tất cả các ô!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
//        if (StringUtils.isEmpty(cus.getUserName()) || StringUtils.isEmpty(cus.getPassword()) || StringUtils.isEmpty(checkCaptcha)) {
//            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_WARNING, "Chú ý", "Vui lòng điền tất cả các ô!");
//            mm.put("MESSAGE_PAGINATION", mP);
//            return mAV;
//        }
//        Map captchaList = (Map) request.getSession().getAttribute("CAPTCHA");
//        String triAnCaptcha = (String) captchaList.get("CUSTOMER_LOGIN");
//        checkCaptcha = checkCaptcha.toLowerCase();
//        if (checkCaptcha == null || !CustomFunction.md5(checkCaptcha).equals(triAnCaptcha)) {
//            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Mã captcha không hợp lệ!");
//            mm.put("MESSAGE_PAGINATION", mP);
//            return mAV;
//        }
        cus.setPassword(CustomFunction.md5(cus.getPassword()));
        try {
            cus = new CustomerFacade().login(cus);
            if (cus == null) {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Sai tên đăng nhập hoặc mật khẩu!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            if (!cus.getIsActive()) {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Cảnh bảo", "Tài khoản của bạn chưa được kích hoạt!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            if (cus.getIsLock()) {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Cảnh bảo", "Tài khoản của bạn đã bị khóa!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            request.getSession().setAttribute("CUSTOMER_ID", cus.getId());
//            request.getSession().setAttribute("CUSTOMER_PIN_CODE", cus.getPinCode());
//            request.getSession().setAttribute("CUSTOMER_ACTIVE_TIME", cus.getLastLoginDateUtc());
            mm.put("REDIRECT_URL", "/trang-chu");
            mAV = new ModelAndView(DefaultIndexPagination.REDIRECT_FOLDER + DefaultIndexPagination.REDIRECT_VIEW);
            return mAV;
        } catch (Exception e) {
            e.printStackTrace();
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
    }

    @RequestMapping(value = "/Register", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView register(@RequestBody CustomerNonActive customerNonActive, ModelMap mm, HttpServletRequest request) {
        ModelAndView mAV = new ModelAndView(DefaultIndexPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        Boolean allowRegister = Boolean.valueOf(ConfigUtils.getInstance().getProperty(request.getServletContext().getRealPath("/WEB-INF/Config.properties"), "allowRegister"));
        if (!allowRegister) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Chức năng đăng ký đang tạm thời ngừng hoạt động!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        Calendar dateOfBirth = Calendar.getInstance();
        try {
            dateOfBirth.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(customerNonActive.getDateOfBirth()));
            if (dateOfBirth.get(Calendar.YEAR) >= 1997) {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đăng ký không thành công. Bạn chưa đủ tuổi tham gia hệ thống này!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
        } catch (Exception e) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Định dạng ngày tháng không hợp lệ. Định dạng yêu cầu: dd/mm/yyyy. VD: 01/01/1990!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        customerNonActive.setUserName(customerNonActive.getTitle());
        int result;
        try {
            result = new CustomerFacade().create(customerNonActive);
        } catch (Exception ex) {
            ex.printStackTrace();
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        switch (result) {
            case 1: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Người dùng không hợp lệ!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 2: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_WARNING, "Chú ý", "Yêu cầu nhập tất cả các thông tin được yêu cầu!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 3: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Tên đăng nhập nhanh đã tồn tại!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 4: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Người chỉ định không hợp lệ!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 5: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Người chỉ định đã đủ 2 nhánh!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 6: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Người giới thiệu không hợp lệ!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 7: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Không chọn đúng mã chủ!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 8: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Không được sử dụng số CMND của mã chủ!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 9: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Số CMND không nằm trong một hệ thống!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 10: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Người chỉ định và người giới thiệu phải trong cùng một hệ thống!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 11: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Đăng ký thành công, Username: " + customerNonActive.getUserName() + "!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            default: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Thử lại sau!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
        }
    }

    //My account
    @RequestMapping(value = "/MyAccount", method = RequestMethod.GET)
    public ModelAndView myAccount(HttpServletRequest request) {
        return new ModelAndView("includes/index/ajax_content/customer_my_account");
    }

    //My account
    @RequestMapping(value = "/MyAccount/Edit", method = RequestMethod.POST)
    public ModelAndView editAccount(@RequestBody Map map, HttpServletRequest request, ModelMap mm) {
        ModelAndView mAV = new ModelAndView(DefaultIndexPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        Boolean allowRegister = Boolean.valueOf(ConfigUtils.getInstance().getProperty(request.getServletContext().getRealPath("/WEB-INF/Config.properties"), "allowUpdateInfo"));
        if (!allowRegister) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Chức năng cập nhật thông tin đã ngừng hoạt động!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        try {
            new CustomerFacade().editCustomerByCustomer(map, (int) request.getSession().getAttribute("CUSTOMER_ID"));
        } catch (Exception e) {
            e.printStackTrace();
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Cập nhật thông tin thành công!");
        mm.put("MESSAGE_PAGINATION", mP);
        return mAV;
    }//My account
    
    @RequestMapping(value = "/MyAccount/EditTaxCode", method = RequestMethod.POST)
    public ModelAndView editTaxCode(@RequestBody Map map, HttpServletRequest request, ModelMap mm) {
        ModelAndView mAV = new ModelAndView(DefaultIndexPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        Boolean allowRegister = Boolean.valueOf(ConfigUtils.getInstance().getProperty(request.getServletContext().getRealPath("/WEB-INF/Config.properties"), "allowUpdateTaxCode"));
        if (!allowRegister) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Chức năng cập nhật mã số thuế đã ngừng hoạt động!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        try {
            new CustomerFacade().editTaxCodeByCustomer(map, (int) request.getSession().getAttribute("CUSTOMER_ID"));
        } catch (Exception e) {
            e.printStackTrace();
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Cập nhật mã số thuế thành công!");
        mm.put("MESSAGE_PAGINATION", mP);
        return mAV;
    }

    //Tree Customer
    @RequestMapping(value = "/TreeCustomer", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getTreeCustomer(ModelMap mm, HttpSession session) {
        Integer children = (Integer) session.getAttribute("CUSTOMER_ID");
        List tree = null;
        try {
            tree = new CustomerFacade().getTreeCustomer(children, 4);
        } catch (Exception ex) {
            Logger.getLogger(IndexCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        mm.put("LIST_TREE", tree);
        return new ModelAndView(DefaultIndexPagination.AJAX_FOLDER + new CustomerPagination("/customer_tree_customer", "/TreeCustomer").getViewName());
    }

    @RequestMapping(value = "/TreeCustomer/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getTreeCustomer(@PathVariable(value = "id") Integer id) {
        List<CustomerTree> tree = null;
        try {
            tree = new CustomerFacade().getTreeCustomer(id, 2);
            tree = tree.stream().filter(c -> {
                return !Objects.equals(c.getKey(), id);
            }).collect(Collectors.toList());
        } catch (Exception ex) {
            Logger.getLogger(IndexCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return CustomFunction.getJSON(tree);
    }

    @RequestMapping(value = "/TreeFolderCustomer", method = RequestMethod.GET,
            produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public ModelAndView getTreeCustomerView(ModelMap mm, HttpSession session) {
        CustomerPagination treeCustomerPagination = new CustomerPagination("/customer_tree_folder_customer", "/TreeFolderCustomer");
        session.setAttribute("TREE_PAGINATION", treeCustomerPagination);
        Integer cusId = (Integer) session.getAttribute("CUSTOMER_ID");
        Integer parentId = null;
        try {
            parentId = new CustomerFacade().findParentIdFromChildrenId(cusId);
        } catch (Exception ex) {
            Logger.getLogger(IndexCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String tree = CustomFunction.buildTreeCustomer(new CustomerFacade().getTreeCustomer(cusId, 6), parentId, "/Customer/TreeFolderCustomer");
        mm.put("TREE_CUSTOMER", tree);
        return new ModelAndView(DefaultIndexPagination.CONTAINER_FOLDER + treeCustomerPagination.getViewName());
    }

    @RequestMapping(value = "/TreeFolderCustomer/Reload", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView reloadTreeCustomerView(ModelMap mm, HttpSession session) {
        CustomerPagination treeCustomerPagination = (CustomerPagination) session.getAttribute("TREE_PAGINATION");
        if (treeCustomerPagination == null) {
            treeCustomerPagination = new CustomerPagination("/customer_tree_folder_customer", "/TreeFolderCustomer");
            session.setAttribute("TREE_PAGINATION", treeCustomerPagination);
        }
        Integer cusId = (Integer) session.getAttribute("CUSTOMER_ID");
        Integer parentId = null;
        String tree = CustomFunction.buildTreeCustomer(new CustomerFacade().getTreeCustomer(cusId, 6), parentId, "/Customer/TreeFolderCustomer");
        mm.put("TREE_CUSTOMER", tree);
        return new ModelAndView(DefaultIndexPagination.AJAX_FOLDER + treeCustomerPagination.getViewName());
    }

    @RequestMapping(value = "/TreeFolderCustomer/Next/{id}/{fisrtId}", method = RequestMethod.GET,
            produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String nextTreeCustomerView(@PathVariable(value = "id") Integer id, @PathVariable(value = "fisrtId") Integer fisrtId) {
        String tree = CustomFunction.buildTreeCustomer(new CustomerFacade().getTreeCustomer(id, 6), fisrtId, "/Customer/TreeFolderCustomer");
        return tree;
    }

    //CustomerForCustomer
    @RequestMapping(value = "/CustomerForCustomer", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDefaultDistributorView(ModelMap mm, HttpSession session) {
        CustomerPagination customerForCustomerPagination = new CustomerPagination("/customer_for_customer", "/CustomerForCustomer");
        session.setAttribute("INDEX_CUSTOMER_FOR_CUSTOMER_PAGINATION", customerForCustomerPagination);
        return new ModelAndView(DefaultIndexPagination.CONTAINER_FOLDER + customerForCustomerPagination.getViewName());
    }

    @RequestMapping(value = "/CustomerForCustomer/DisplayPerPage/{displayPerPage}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView displayPerPageForDistributorView(@PathVariable("displayPerPage") int displayPerPage, ModelMap mm, HttpSession session) {
        CustomerPagination customerForCustomerPagination = (CustomerPagination) session.getAttribute("INDEX_CUSTOMER_FOR_CUSTOMER_PAGINATION");
        if (customerForCustomerPagination != null) {
            customerForCustomerPagination.setDisplayPerPage(displayPerPage);

        }
        return distributorView(customerForCustomerPagination, session);
    }

    @RequestMapping(value = "/CustomerForCustomer/OrderData/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView orderByDistributorView(@PathVariable("orderBy") String orderBy, ModelMap mm, HttpSession session) {
        CustomerPagination customerForCustomerPagination = (CustomerPagination) session.getAttribute("INDEX_CUSTOMER_FOR_CUSTOMER_PAGINATION");
        if (customerForCustomerPagination != null) {
            if (customerForCustomerPagination.getOrderColmn().equals(orderBy)) {
                customerForCustomerPagination.setAsc(!customerForCustomerPagination.isAsc());
            }
            customerForCustomerPagination.setOrderColmn(orderBy);
        }
        return distributorView(customerForCustomerPagination, session);
    }

    @RequestMapping(value = "/CustomerForCustomer/GoTo/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoDistributorView(@PathVariable("page") int page, ModelMap mm, HttpSession session) {
        CustomerPagination customerForCustomerPagination = (CustomerPagination) session.getAttribute("INDEX_CUSTOMER_FOR_CUSTOMER_PAGINATION");
        if (customerForCustomerPagination != null) {
            customerForCustomerPagination.setCurrentPage(page);
        }
        return distributorView(customerForCustomerPagination, session);
    }

    private ModelAndView distributorView(CustomerPagination customerForCustomerPagination, HttpSession session) {
        if (customerForCustomerPagination == null) {
            customerForCustomerPagination = new CustomerPagination("/customer_for_customer", "/CustomerForCustomer");
        }
        new CustomerFacade().pageData(customerForCustomerPagination, (Integer) session.getAttribute("CUSTOMER_ID"));
        session.setAttribute("INDEX_CUSTOMER_FOR_CUSTOMER_PAGINATION", customerForCustomerPagination);
        return new ModelAndView(DefaultIndexPagination.AJAX_FOLDER + customerForCustomerPagination.getViewName());
    }

    //Auto complete ParentId
    @RequestMapping(value = "/SearchParentId/{searchString}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView searchParentId(@PathVariable("searchString") String searchString, ModelMap mm) {
        mm.put("PARENTIDLIST", new CustomerFacade().findAllCustomerForParentId(searchString));
        return new ModelAndView(DefaultIndexPagination.AJAX_FOLDER + "/customer_parentid_list");
    }

    //Auto complete CustomerId
    @RequestMapping(value = "/SearchCustomerId/{searchString}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView searchCustomerId(@PathVariable("searchString") String searchString, ModelMap mm) {
        mm.put("PARENTIDLIST", new CustomerFacade().findAllCustomerForCustomerId(searchString));
        return new ModelAndView(DefaultIndexPagination.AJAX_FOLDER + "/customer_parentid_list");
    }

    //Auto complete CustomerId
    @RequestMapping(value = "/SearchCustomerId/{searchString}/{parentName}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView searchCustomerId(@PathVariable("searchString") String searchString, @PathVariable("parentName") String parentName, ModelMap mm) {
        mm.put("PARENTIDLIST", new CustomerFacade().findAllCustomerForCustomerId(searchString));
        return new ModelAndView(DefaultIndexPagination.AJAX_FOLDER + "/customer_parentid_list");
    }

    //Change agency view
    @RequestMapping(value = "/ViewChangeAgency", method = RequestMethod.GET)
    public ModelAndView getViewChangeAgency(HttpServletRequest request) {
        return new ModelAndView("includes/index/ajax_content/customer_change_agency");
    }

    //Change agency action
    @RequestMapping(value = "/ChangeAgency", method = RequestMethod.POST)
    public ModelAndView getViewChangeAgency(@RequestBody Map map, ModelMap mm, HttpServletRequest request) {
        ModelAndView mAV = new ModelAndView(DefaultIndexPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        Boolean allowRegister = Boolean.valueOf(ConfigUtils.getInstance().getProperty(request.getServletContext().getRealPath("/WEB-INF/Config.properties"), "allowUpdateAgency"));
        if (!allowRegister) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Chức năng cập nhật đại lý đang tạm ngừng hoạt động!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        HttpSession session = request.getSession();
        try {
            Customer cus = (Customer) new CustomerFacade().find((Integer) session.getAttribute("CUSTOMER_ID"));
            cus.setProvincialAgencies(new ProvincialAgencies(Integer.parseInt(map.get("provinceAgencyId").toString())));
            new CustomerFacade().edit(cus);
        } catch (Exception ex) {
            ex.printStackTrace();
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "Thành công", "Cập nhật đại lý thành công!");
        mm.put("MESSAGE_PAGINATION", mP);
        return mAV;
    }
}
