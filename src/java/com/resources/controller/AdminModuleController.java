package com.resources.controller;

import com.resources.entity.Module;
import com.resources.facade.AdminFacade;
import com.resources.facade.ModuleFacade;
import com.resources.pagination.admin.DefaultAdminPagination;
import com.resources.pagination.admin.MessagePagination;
import com.resources.pagination.admin.ModulePagination;
import com.resources.utils.LeoConstants;
import com.resources.utils.LogUtils;
import com.resources.utils.StringUtils;
import java.util.Map;
import javax.servlet.http.HttpSession;
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

    @RequestMapping(value = "/ListModule/ViewInsert/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getListModuleInsertView(@PathVariable(value = "id") Integer id, ModelMap mm, HttpSession session) {
        Module module = (Module) new ModuleFacade().find(id);
        mm.put("MODULE_PARENT", module);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + "/module_list_module_insert_modal");
    }

    @RequestMapping(value = "/ListModule/Insert", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView insertModule(@RequestBody Map module, ModelMap mm, HttpSession session) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        Integer result;
        try {
            Integer role = new AdminFacade().getAdminRoleByAdminId((Integer) session.getAttribute("ADMIN_ID"));
            if (role != 1) {
                LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_DELETE, 56, "Thêm mới module, lỗi");
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Bạn không đủ quyền để thực hiện hành động này!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            result = new ModuleFacade().create(module);
            LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_DELETE, 56, "Thêm mới module: " + result + ", thành công");
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "Thành công", "Thêm mới module thành công!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_DELETE, 56, "Thêm mới module, lỗi");
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Vui lòng thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
    }
    
    @RequestMapping(value = "/ListModule/ViewEdit/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getListModuleEditView(@PathVariable(value = "id") Integer id, ModelMap mm, HttpSession session) {
        Module module = (Module) new ModuleFacade().find(id);
        mm.put("MODULE_EDIT", module);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + "/module_list_module_edit_modal");
    }
    
    @RequestMapping(value = "/ListModule/Edit", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView editModule(@RequestBody Map module, ModelMap mm, HttpSession session) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        try {
            Integer role = new AdminFacade().getAdminRoleByAdminId((Integer) session.getAttribute("ADMIN_ID"));
            if (role != 1) {
                LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_DELETE, 56, "Thêm mới module, lỗi");
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Bạn không đủ quyền để thực hiện hành động này!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            new ModuleFacade().edit(module);
            LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_DELETE, 56, "Cập nhật module: " + StringUtils.escapeHtmlEntity(module.get("id").toString()) + ", thành công");
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "Thành công", "Cập nhật module thành công!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_DELETE, 56, "Cập nhật module: " + StringUtils.escapeHtmlEntity(module.get("id").toString()) + ", lỗi");
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Vui lòng thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
    }

    @RequestMapping(value = "/ListModule/Delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView deleteCustomer(@PathVariable(value = "id") Integer id, ModelMap mm, HttpSession session) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        try {
            Integer role = new AdminFacade().getAdminRoleByAdminId((Integer) session.getAttribute("ADMIN_ID"));
            if (role != 1) {
                LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_DELETE, 56, "Xóa module id: " + id + ", lỗi");
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Bạn không đủ quyền để thực hiện hành động này!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            Module module = (Module) new ModuleFacade().find(id);
            module.setIsDeleted(true);
            new ModuleFacade().edit(module);
            LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_DELETE, 56, "Xóa module id: " + id + ", thành công");
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "Thành công", "Xóa module thành công!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        } catch (Exception e) {
            LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_DELETE, 56, "Xóa module id: " + id + ", lỗi");
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Vui lòng thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
    }

    @RequestMapping(value = "/ListModule/ChangeStatus/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeStatusCustomer(@PathVariable(value = "id") Integer id, ModelMap mm, HttpSession session) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        Module module = null;
        try {
            Integer role = new AdminFacade().getAdminRoleByAdminId((Integer) session.getAttribute("ADMIN_ID"));
            if (role != 1) {
                LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_DELETE, 56, module != null && module.getIsShow() ? "Mở khóa" : "Khóa" + "Xóa module id: " + id + ", lỗi");
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Bạn không đủ quyền để thực hiện hành động này!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            module = (Module) new ModuleFacade().find(id);
            module.setIsShow(!module.getIsShow());
            new ModuleFacade().edit(module);
            LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_DELETE, 56, module.getIsShow() ? "Mở khóa" : "Khóa" + " module id: " + id + ", thành công");
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "Thành công", module.getIsShow() ? "Mở khóa" : "Khóa" + " module thành công!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        } catch (Exception e) {
            LogUtils.logs((Integer) session.getAttribute("ADMIN_ID"), LeoConstants.ActionConstants.ACTION_DELETE, 56, module != null && module.getIsShow() ? "Mở khóa" : "Khóa" + " module id: " + id + ", lỗi");
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi! Vui lòng thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
    }
}
