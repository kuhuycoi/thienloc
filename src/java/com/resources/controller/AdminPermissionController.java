package com.resources.controller;

import com.google.gson.Gson;
import com.resources.entity.Admin;
import com.resources.entity.ModuleInRole;
import com.resources.entity.RoleAdmin;
import com.resources.facade.AdminFacade;
import com.resources.facade.CustomerFacade;
import com.resources.facade.ModuleFacade;
import com.resources.facade.ModuleInRoleFacade;
import com.resources.facade.RoleAdminFacade;
import com.resources.pagination.admin.AdminPagination;
import com.resources.pagination.admin.DefaultAdminPagination;
import com.resources.pagination.admin.MessagePagination;
import com.resources.utils.LeoConstants;
import com.resources.utils.LogUtils;
import com.resources.utils.StringUtils;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
@RequestMapping(value = "/Admin/Permission")
public class AdminPermissionController {

    //ListAdmin    
    @RequestMapping(value = "/ListAdmin", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDefaultListAdminView(ModelMap mm, HttpSession session) {
        AdminPagination adminPagination = (AdminPagination) session.getAttribute("LIST_ADMIN_PAGINATION");
        if (adminPagination == null) {
            adminPagination = new AdminPagination("/permission_list_admin", "/ListAdmin");
        }
        session.setAttribute("LIST_ADMIN_PAGINATION", adminPagination);
        return new ModelAndView(DefaultAdminPagination.CONTAINER_FOLDER + adminPagination.getViewName());
    }

    @RequestMapping(value = "/ListAdmin/DisplayPerPage/{displayPerPage}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeDisplayPerPageForListAdminView(@PathVariable("displayPerPage") int displayPerPage, HttpSession session, ModelMap mm) {
        AdminPagination adminPagination = (AdminPagination) session.getAttribute("LIST_ADMIN_PAGINATION");
        if (adminPagination != null) {
            adminPagination.setDisplayPerPage(displayPerPage);
        }
        return listAdminView(adminPagination, session);
    }

    @RequestMapping(value = "/ListAdmin/OrderData/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView orderByListAdminView(@PathVariable("orderBy") String orderBy, ModelMap mm, HttpSession session) {
        AdminPagination adminPagination = (AdminPagination) session.getAttribute("LIST_ADMIN_PAGINATION");
        if (adminPagination != null) {
            if (adminPagination.getOrderColmn().equals(orderBy)) {
                adminPagination.setAsc(!adminPagination.isAsc());
            }
            adminPagination.setOrderColmn(orderBy);
        }
        return listAdminView(adminPagination, session);
    }

    @RequestMapping(value = "/ListAdmin/GoTo/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoListAdminView(@PathVariable("page") int page, ModelMap mm, HttpSession session) {
        AdminPagination adminPagination = (AdminPagination) session.getAttribute("LIST_ADMIN_PAGINATION");
        if (adminPagination != null) {
            adminPagination.setCurrentPage(page);
        }
        return listAdminView(adminPagination, session);
    }

    @RequestMapping(value = "/ListAdmin/Search", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public ModelAndView searchDistributorView(@RequestBody Map map, HttpSession session) {
        String searchString = (String) map.get("searchString");
        if (StringUtils.isEmpty(searchString)) {
            return listAdminView(null, session);
        }
        List<String> keywords = (List) map.get("keywords");
        AdminPagination adminPagination = new AdminPagination("/permission_list_admin", "/ListAdmin");
        adminPagination.setSearchString(searchString);
        adminPagination.setKeywords(keywords);
        return listAdminView(adminPagination, session);
    }

    private ModelAndView listAdminView(AdminPagination adminPagination, HttpSession session) {
        if (adminPagination == null) {
            adminPagination = new AdminPagination("/permission_list_admin", "/ListAdmin");
        }
        new AdminFacade().pageData(adminPagination);
        session.setAttribute("LIST_ADMIN_PAGINATION", adminPagination);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + adminPagination.getViewName());
    }

    @RequestMapping(value = "/ListAdmin/ViewInsert", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getListAdminViewInsert(HttpSession session) {
        AdminPagination adminPagination = (AdminPagination) session.getAttribute("LIST_ADMIN_PAGINATION");
        if (adminPagination == null) {
            adminPagination = new AdminPagination("/permission_list_admin", "/ListAdmin");
        }
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + adminPagination.getInsertViewName());
    }

    @RequestMapping(value = "/ListAdmin/ViewEdit/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getListAdminViewInsert(@PathVariable(value = "id") Integer id, ModelMap mm, HttpSession session) {
        Admin admin = (Admin) new AdminFacade().find(id);
        mm.put("ADMIN_EDIT", admin);
        AdminPagination adminPagination = (AdminPagination) session.getAttribute("LIST_ADMIN_PAGINATION");
        if (adminPagination == null) {
            adminPagination = new AdminPagination("/permission_list_admin", "/ListAdmin");
        }
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + adminPagination.getEditViewName());
    }

    @RequestMapping(value = "/ListAdmin/Insert", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView register(@RequestBody Map admin, ModelMap mm, HttpServletRequest request) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        int result;
        try {
            result = new AdminFacade().create(admin);
        } catch (Exception e) {
            e.printStackTrace();
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        switch (result) {
            case 1: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Thử lại sau!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 2: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_WARNING, "Chú ý", "Yêu cầu nhập tất cả các thông tin được yêu cầu!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 3: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Tên đăng nhập đã tồn tại!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            case 4: {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Đăng ký thành công!");
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

    @RequestMapping(value = "/ListAdmin/Edit", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView edit(@RequestBody Map admin, ModelMap mm, HttpSession session) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        int result;
        Integer role = new AdminFacade().getAdminRoleByAdminId((Integer) session.getAttribute("ADMIN_ID"));
        try {
            result = new AdminFacade().edit(admin, role);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_UPDATE, 9, "Cập nhật quản trị viên id:'" + Integer.parseInt(admin.get("id").toString()) + ", Lỗi");
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

    @RequestMapping(value = "/ListAdmin/Delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView delete(@PathVariable Integer id, ModelMap mm, HttpSession session) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        Integer myId = (Integer) session.getAttribute("ADMIN_ID");
        if (Objects.equals(myId, id)) {
            LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_DELETE, 9, "Xóa quản trị viên id:'" + myId + ", Lỗi: Bạn không thể thực hiện hành động này!");
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Bạn không thể thực hiện hành động này!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        try {
            new AdminFacade().delete(id);
        } catch (Exception e) {
            LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_DELETE, 9, "Xóa quản trị viên id:'" + myId + ", Lỗi");
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_DELETE, 9, "Xóa quản trị viên id:'" + myId + ", Thành công");
        mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Xóa quản trị viên thành công!");
        mm.put("MESSAGE_PAGINATION", mP);
        return mAV;
    }

    @RequestMapping(value = "/ListAdmin/Block/{id}/{status}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView block(@PathVariable Integer id, @PathVariable Boolean status,
            ModelMap mm, HttpSession session) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        Integer myId = (Integer) session.getAttribute("ADMIN_ID");
        if (Objects.equals(myId, id)) {
            LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_UPDATE, 9, "Khóa quản trị viên id:'" + myId + ", Lỗi: Bạn không thể thực hiện hành động này!");
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Bạn không thể thực hiện hành động này!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        try {
            new AdminFacade().block(id, status);
        } catch (Exception e) {
            LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_UPDATE, 9, "Khóa quản trị viên id:'" + myId + ", Lỗi!");
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_UPDATE, 9, "Khóa quản trị viên id:'" + myId + ", Thành công!");
        mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Khóa quản trị viên thành công!");
        mm.put("MESSAGE_PAGINATION", mP);
        return mAV;
    }

    //RoleAdmin    
    @RequestMapping(value = "/RoleAdmin", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDefaultRoleAdminView(ModelMap mm, HttpSession session) {
        AdminPagination adminPagination = (AdminPagination) session.getAttribute("ROLE_ADMIN_PAGINATION");
        if (adminPagination == null) {
            adminPagination = new AdminPagination("/permission_role_admin", "/RoleAdmin");
            adminPagination.setViewTitle("Danh sách quyền quản trị");
            adminPagination.setInsertViewName("/permission_role_admin_insert_modal");
        }
        session.setAttribute("ROLE_ADMIN_PAGINATION", adminPagination);
        return new ModelAndView(DefaultAdminPagination.CONTAINER_FOLDER + adminPagination.getViewName());
    }

    @RequestMapping(value = "/RoleAdmin/DisplayPerPage/{displayPerPage}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeDisplayPerPageForRoleAdminView(@PathVariable("displayPerPage") int displayPerPage, HttpSession session, ModelMap mm) {
        AdminPagination adminPagination = (AdminPagination) session.getAttribute("ROLE_ADMIN_PAGINATION");
        if (adminPagination != null) {
            adminPagination.setDisplayPerPage(displayPerPage);
        }
        return roleAdminView(adminPagination, session);
    }

    @RequestMapping(value = "/RoleAdmin/OrderData/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView orderByRoleAdminView(@PathVariable("orderBy") String orderBy, ModelMap mm, HttpSession session) {
        AdminPagination adminPagination = (AdminPagination) session.getAttribute("ROLE_ADMIN_PAGINATION");
        if (adminPagination != null) {
            if (adminPagination.getOrderColmn().equals(orderBy)) {
                adminPagination.setAsc(!adminPagination.isAsc());
            }
            adminPagination.setOrderColmn(orderBy);
        }
        return roleAdminView(adminPagination, session);
    }

    @RequestMapping(value = "/RoleAdmin/GoTo/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoRoleAdminView(@PathVariable("page") int page, ModelMap mm, HttpSession session) {
        AdminPagination adminPagination = (AdminPagination) session.getAttribute("ROLE_ADMIN_PAGINATION");
        if (adminPagination != null) {
            adminPagination.setCurrentPage(page);
        }
        return roleAdminView(adminPagination, session);
    }

    @RequestMapping(value = "/RoleAdmin/Search", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public ModelAndView searchRoleAdminView(@RequestBody Map map, HttpSession session) {
        String searchString = (String) map.get("searchString");
        if (StringUtils.isEmpty(searchString)) {
            return listAdminView(null, session);
        }
        List<String> keywords = (List) map.get("keywords");
        AdminPagination adminPagination = new AdminPagination("/permission_role_admin", "/RoleAdmin");
        adminPagination.setViewTitle("Danh sách quyền quản trị");
        adminPagination.setInsertViewName("/permission_role_admin_insert_modal");
        adminPagination.setSearchString(searchString);
        adminPagination.setKeywords(keywords);
        return roleAdminView(adminPagination, session);
    }

    private ModelAndView roleAdminView(AdminPagination adminPagination, HttpSession session) {
        if (adminPagination == null) {
            adminPagination = new AdminPagination("/permission_role_admin", "/RoleAdmin");
        }
        new RoleAdminFacade().pageData(adminPagination);
        session.setAttribute("ROLE_ADMIN_PAGINATION", adminPagination);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + adminPagination.getViewName());
    }

    @RequestMapping(value = "/RoleAdmin/ViewInsert", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getRoleAdminViewInsert(HttpSession session) {
        AdminPagination adminPagination = (AdminPagination) session.getAttribute("ROLE_ADMIN_PAGINATION");
        if (adminPagination == null) {
            adminPagination = new AdminPagination("/permission_role_admin", "/RoleAdmin");
        }
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + adminPagination.getInsertViewName());
    }

    @RequestMapping(value = "/RoleAdmin/ViewEdit/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getRoleAdminViewInsert(@PathVariable(value = "id") Integer id, ModelMap mm, HttpSession session) {
        Admin admin = (Admin) new AdminFacade().find(id);
        mm.put("ADMIN_EDIT", admin);
        AdminPagination adminPagination = (AdminPagination) session.getAttribute("ROLE_ADMIN_PAGINATION");
        if (adminPagination == null) {
            adminPagination = new AdminPagination("/permission_list_admin", "/ListAdmin");
        }
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + adminPagination.getEditViewName());
    }

    @RequestMapping(value = "/RoleAdmin/Insert", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView insertRoleAdmin(@RequestBody RoleAdmin roleAdmin, ModelMap mm, HttpServletRequest session) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        try {
            roleAdmin = (RoleAdmin) new RoleAdminFacade().create(roleAdmin);
            LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_DELETE, 9, "Thêm mới quyền quản trị id:'" + roleAdmin.getId() + ", Thành công");
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Thêm mới quyền thành công!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_DELETE, 9, "Thêm mới quyền quản trị id:'" + roleAdmin.getId() + ", Lỗi");
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
    }

//    @RequestMapping(value = "/RoleAdmin/Edit", method = RequestMethod.POST)
//    @ResponseBody
//    public ModelAndView editRoleAdmin(@RequestBody Map admin, ModelMap mm, HttpServletRequest request) {
//        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
//        MessagePagination mP;
//        int result;
//        try {
//            result = new AdminFacade().edit(admin);
//        } catch (Exception e) {
//            e.printStackTrace();
//            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Thử lại sau!");
//            mm.put("MESSAGE_PAGINATION", mP);
//            return mAV;
//        }
//        switch (result) {
//            case 1: {
//                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Thử lại sau!");
//                mm.put("MESSAGE_PAGINATION", mP);
//                return mAV;
//            }
//            case 2: {
//                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_WARNING, "Chú ý", "Yêu cầu nhập tất cả các thông tin được yêu cầu!");
//                mm.put("MESSAGE_PAGINATION", mP);
//                return mAV;
//            }
//            case 3: {
//                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Cập nhật thành công!");
//                mm.put("MESSAGE_PAGINATION", mP);
//                return mAV;
//            }
//            default: {
//                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Thử lại sau!");
//                mm.put("MESSAGE_PAGINATION", mP);
//                return mAV;
//            }
//        }
//    }
//    @RequestMapping(value = "/RoleAdmin/Delete/{id}", method = RequestMethod.GET)
//    @ResponseBody
//    public ModelAndView deleteRoleAdmin(@PathVariable Integer id, ModelMap mm, HttpSession session) {
//        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
//        MessagePagination mP;
//        Admin myId = (Admin) session.getAttribute("ADMIN");
//        if (Objects.equals(myId.getId(), id)) {
//            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Bạn không thể thực hiện hành động này!");
//            mm.put("MESSAGE_PAGINATION", mP);
//            return mAV;
//        }
//        try {
//            new AdminFacade().delete(id);
//        } catch (Exception e) {
//            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Thử lại sau!");
//            mm.put("MESSAGE_PAGINATION", mP);
//            return mAV;
//        }
//        mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Xóa quản trị viên thành công!");
//        mm.put("MESSAGE_PAGINATION", mP);
//        return mAV;
//    }
//    @RequestMapping(value = "/RoleAdmin/Block/{id}/{status}", method = RequestMethod.GET)
//    @ResponseBody
//    public ModelAndView blockRoleAdmin(@PathVariable Integer id, @PathVariable Boolean status,
//            ModelMap mm, HttpSession session) {
//        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
//        MessagePagination mP;
//        Admin myId = (Admin) session.getAttribute("ADMIN");
//        if (Objects.equals(myId.getId(), id)) {
//            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Bạn không thể thực hiện hành động này!");
//            mm.put("MESSAGE_PAGINATION", mP);
//            return mAV;
//        }
//        try {
//            new AdminFacade().block(id, status);
//        } catch (Exception e) {
//            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Thử lại sau!");
//            mm.put("MESSAGE_PAGINATION", mP);
//            return mAV;
//        }
//        mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Khóa quản trị viên thành công!");
//        mm.put("MESSAGE_PAGINATION", mP);
//        return mAV;
//    }
    @RequestMapping(value = "/RoleAdmin/ViewChangePermission/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeRoleAdmin(@PathVariable Integer id, ModelMap mm, HttpSession session) {
        RoleAdmin roleAdmin = new RoleAdminFacade().find(id);
        List<ModuleInRole> moduleInRoles = roleAdmin.getModuleInRoles();
        mm.put("MODULE_IN_ROLES_CHECK", moduleInRoles);
        mm.put("MODULES", new ModuleFacade().findAllModuleByLevel(1));
        mm.put("CURRENT_ROLE", id);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + "/permission_change_permission_modal");
    }
//Reset Password

    @RequestMapping(value = {"/RoleAdmin/ChangePermission/{id}"}, method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView resetPassword(@PathVariable(value = "id") Integer id, @RequestBody Integer[] ids, ModelMap mm, HttpSession session) {
        MessagePagination mP;
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        try {
            new ModuleInRoleFacade().create(id, ids);
        } catch (Exception e) {
            LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_UPDATE, 9, "Update quyền quản trị id:'" + id + ", Lỗi!");
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_UPDATE, 9, "Update quyền quản trị id:'" + id + ", Thành công!");
        mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Update quyền quản trị thành công!");
        mm.put("MESSAGE_PAGINATION", mP);
        return mAV;
    }
}
