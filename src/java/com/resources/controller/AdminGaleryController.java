package com.resources.controller;

import com.resources.entity.Admin;
import com.resources.entity.Galery;
import com.resources.entity.Images;
import com.resources.facade.GaleryFacade;
import com.resources.facade.ImagesFacade;
import com.resources.pagination.admin.DefaultAdminPagination;
import com.resources.pagination.admin.GaleryPagination;
import com.resources.pagination.admin.MessagePagination;
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
@RequestMapping(value = "/Admin/Galery")
public class AdminGaleryController {

    @RequestMapping(value = "/GaleryList")
    @ResponseBody
    public ModelAndView getGaleryView(ModelMap mm, HttpSession session) {
        GaleryPagination pagination = new GaleryPagination("/galery_category", "/GaleryList");
        session.setAttribute("GALERY_CATEGORY_PAGINATION", pagination);
        return new ModelAndView(DefaultAdminPagination.CONTAINER_FOLDER + pagination.getViewName());
    }
    
    @RequestMapping(value = "/GaleryList/DisplayPerPage/{displayPerPage}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView displayPerPageForGaleryView(@PathVariable("displayPerPage") int displayPerPage, HttpSession session) {
        GaleryPagination pagination = (GaleryPagination) session.getAttribute("GALERY_CATEGORY_PAGINATION");
        if (pagination != null) {
            pagination.setDisplayPerPage(displayPerPage);
        }
        return galeryView(pagination, session);
    }

    @RequestMapping(value = "/GaleryList/OrderData/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView orderByGaleryView(@PathVariable("orderBy") String orderBy, ModelMap mm, HttpSession session) {
        GaleryPagination pagination = (GaleryPagination) session.getAttribute("GALERY_CATEGORY_PAGINATION");
        if (pagination != null) {
            if (pagination.getOrderColmn().equals(orderBy)) {
                pagination.setAsc(!pagination.isAsc());
            }
            pagination.setOrderColmn(orderBy);
        }
        return galeryView(pagination, session);
    }

    @RequestMapping(value = "/GaleryList/GoTo/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoGaleryView(@PathVariable("page") int page, HttpSession session) {
        GaleryPagination pagination = (GaleryPagination) session.getAttribute("GALERY_CATEGORY_PAGINATION");
        if (pagination != null) {
            pagination.setCurrentPage(page);
        }
        return galeryView(pagination, session);
    }

    @RequestMapping(value = "/GaleryList/Search", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public ModelAndView searchGaleryView(@RequestBody Map map, HttpSession session) {
        String searchString = (String) map.get("searchString");
        if (StringUtils.isEmpty(searchString)) {
            return galeryView(null, session);
        }
        List<String> keywords = (List) map.get("keywords");
        GaleryPagination pagination = new GaleryPagination("/galery_category", "/GaleryList");
        pagination.setSearchString(searchString);
        pagination.setKeywords(keywords);
        return galeryView(pagination, session);
    }

    private ModelAndView galeryView(GaleryPagination pagination, HttpSession session) {
        if (pagination == null) {
            pagination = new GaleryPagination("/galery_category", "/GaleryList");
        }
        new GaleryFacade().pageData(pagination);
        session.setAttribute("GALERY_CATEGORY_PAGINATION", pagination);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + pagination.getViewName());
    }
    

    @RequestMapping(value = "GaleryList/ViewInsert")
    @ResponseBody
    public ModelAndView getListPostViewInsert(ModelMap mm, HttpSession session) {
        GaleryPagination pagination = (GaleryPagination) session.getAttribute("GALERY_CATEGORY_PAGINATION");
        pagination.setInsertViewName("/galery_category_insert_modal");
        session.setAttribute("GALERY_CATEGORY_PAGINATION", pagination);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + pagination.getInsertViewName());
    }

    @RequestMapping(value = "GaleryList/ViewEdit/{id}")
    @ResponseBody
    public ModelAndView getListPostViewEdit(@PathVariable(value = "id") Integer id, ModelMap mm, HttpSession session) {
        GaleryPagination pagination = (GaleryPagination) session.getAttribute("GALERY_CATEGORY_PAGINATION");
        pagination.setEditViewName("/galery_category_edit_modal");
        session.setAttribute("GALERY_CATEGORY_PAGINATION", pagination);
        Galery galery = (Galery) new GaleryFacade().find(id);
        mm.put("GALERY", galery);
        if (galery == null || galery.getIsDelete()) {
            return null;
        }
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + pagination.getEditViewName());
    }
    
    
    @RequestMapping(value = "/GaleryList/Insert", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView insertGalery(@RequestBody Map map, ModelMap mm, HttpSession session) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        try {
            Galery galery = new Galery();
            galery.setName(map.get("name") == null ? null : map.get("name").toString());
            galery.setTitleImg(map.get("titleImg") == null ? null : map.get("titleImg").toString());
            galery.setIsShow(Boolean.valueOf(map.get("isShow") == null ? null : map.get("isShow").toString()));
            galery.setOrderNumber(map.get("orderNumber") == null ? null : map.get("orderNumber") == null ? null : Integer.parseInt(map.get("orderNumber").toString()));
            galery.setShortDescription(map.get("shortDescription") == null ? null : map.get("shortDescription").toString());
            galery.setCreatedAdm(new Admin(Integer.parseInt(session.getAttribute("ADMIN_ID").toString())));
            new GaleryFacade().create(galery);
        } catch (Exception ex) {
            ex.printStackTrace();
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Thêm mới Album ảnh thành công!");
        mm.put("MESSAGE_PAGINATION", mP);
        return mAV;
    }
    
    @RequestMapping(value = "/GaleryList/Edit", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView editGalery(@RequestBody Map map, ModelMap mm, HttpSession session) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        try {
            Galery galery = new Galery();
            galery.setId(Integer.parseInt(map.get("id").toString()));
            galery.setName(map.get("name") == null ? null : map.get("name").toString());
            galery.setTitleImg(map.get("titleImg") == null ? null : map.get("titleImg").toString());
            galery.setIsShow(Boolean.valueOf(map.get("isShow") == null ? null : map.get("isShow").toString()));
            galery.setOrderNumber(map.get("orderNumber") == null ? null : map.get("orderNumber") == null ? null : Integer.parseInt(map.get("orderNumber").toString()));
            galery.setShortDescription(map.get("shortDescription") == null ? null : map.get("shortDescription").toString());
            galery.setIsDelete(false);
            new GaleryFacade().edit(galery);
        } catch (Exception ex) {
            ex.printStackTrace();
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Cập nhật Album ảnh thành công!");
        mm.put("MESSAGE_PAGINATION", mP);
        return mAV;
    }
    
    @RequestMapping(value = "/GaleryList/Delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView deleteGalery(@PathVariable("id") int id, HttpSession session, ModelMap mm) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        try {
            Galery galery = (Galery) new GaleryFacade().find(id);
            if (galery == null || galery.getIsDelete()) {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Album không tồn tại!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            galery.setIsDelete(true);
            new GaleryFacade().edit(galery);

            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Xóa album thành công");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        } catch (Exception ex) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
    }

    @RequestMapping(value = "/GaleryList/Hide/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView hide(@PathVariable("id") int id, HttpSession session, ModelMap mm) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        try {
            Galery galery = (Galery) new GaleryFacade().find(id);
            if (galery == null || galery.getIsDelete()) {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Album không tồn tại!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            galery.setIsShow(!galery.getIsShow());
            new GaleryFacade().edit(galery);

            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", galery.getIsShow() ? "Hiện album thành công" : "Ẩn album thành công");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        } catch (Exception ex) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
    }
    
    
    @RequestMapping(value = "/Images")
    @ResponseBody
    public ModelAndView getImagesView(ModelMap mm, HttpSession session) {
        GaleryPagination pagination = new GaleryPagination("/galery_images", "/Images");
        pagination.setViewTitle("danh sách ảnh");
        session.setAttribute("GALERY_IMAGES_PAGINATION", pagination);
        return new ModelAndView(DefaultAdminPagination.CONTAINER_FOLDER + pagination.getViewName());
    }
    
    @RequestMapping(value = "/Images/DisplayPerPage/{displayPerPage}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView displayPerPageForImagesView(@PathVariable("displayPerPage") int displayPerPage, HttpSession session) {
        GaleryPagination pagination = (GaleryPagination) session.getAttribute("GALERY_CATEGORY_PAGINATION");
        if (pagination != null) {
            pagination.setDisplayPerPage(displayPerPage);
        }
        return imagesView(pagination, session);
    }

    @RequestMapping(value = "/Images/OrderData/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView orderByImagesView(@PathVariable("orderBy") String orderBy, ModelMap mm, HttpSession session) {
        GaleryPagination pagination = (GaleryPagination) session.getAttribute("GALERY_IMAGES_PAGINATION");
        if (pagination != null) {
            if (pagination.getOrderColmn().equals(orderBy)) {
                pagination.setAsc(!pagination.isAsc());
            }
            pagination.setOrderColmn(orderBy);
        }
        return imagesView(pagination, session);
    }

    @RequestMapping(value = "/Images/GoTo/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoImagesView(@PathVariable("page") int page, HttpSession session) {
        GaleryPagination pagination = (GaleryPagination) session.getAttribute("GALERY_IMAGES_PAGINATION");
        if (pagination != null) {
            pagination.setCurrentPage(page);
        }
        return imagesView(pagination, session);
    }

    @RequestMapping(value = "/Images/Search", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public ModelAndView searchImagesView(@RequestBody Map map, HttpSession session) {
        String searchString = (String) map.get("searchString");
        if (StringUtils.isEmpty(searchString)) {
            return galeryView(null, session);
        }
        List<String> keywords = (List) map.get("keywords");
        GaleryPagination pagination = new GaleryPagination("/galery_images", "/Images");
        pagination.setViewTitle("danh sách ảnh");
        pagination.setSearchString(searchString);
        pagination.setKeywords(keywords);
        return imagesView(pagination, session);
    }

    private ModelAndView imagesView(GaleryPagination pagination, HttpSession session) {
        if (pagination == null) {
            pagination = new GaleryPagination("/galery_images", "/Images");
        }
        new ImagesFacade().pageData(pagination);
        session.setAttribute("GALERY_IMAGES_PAGINATION", pagination);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + pagination.getViewName());
    }
    
    @RequestMapping(value = "/Images/ViewInsert")
    @ResponseBody
    public ModelAndView getImagesViewInsert(ModelMap mm, HttpSession session) {
        GaleryPagination pagination = (GaleryPagination) session.getAttribute("GALERY_IMAGES_PAGINATION");
        pagination.setInsertViewName("/galery_images_insert_modal");
        session.setAttribute("GALERY_IMAGES_PAGINATION", pagination);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + pagination.getInsertViewName());
    }
    
    @RequestMapping(value = "/Images/Insert", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView insertImages(@RequestBody Map map, ModelMap mm, HttpSession session) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        try {
            Images images = new Images();
            images.setName(map.get("name") == null ? null : map.get("name").toString());
            images.setPath(map.get("path") == null ? null : map.get("path").toString());
            images.setIsShow(Boolean.valueOf(map.get("isShow") == null ? null : map.get("isShow").toString()));
            images.setOrderNumber(map.get("orderNumber") == null ? null : map.get("orderNumber") == null ? null : Integer.parseInt(map.get("orderNumber").toString()));
            images.setDescription(map.get("description") == null ? null : map.get("description").toString());
            images.setGaleryId(new Galery(Integer.parseInt(map.get("galeryId").toString())));
            images.setCreatedAdm(new Admin(Integer.parseInt(session.getAttribute("ADMIN_ID").toString())));
            new ImagesFacade().create(images);
        } catch (Exception ex) {
            ex.printStackTrace();
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Thêm mới ảnh thành công!");
        mm.put("MESSAGE_PAGINATION", mP);
        return mAV;
    }
    
    @RequestMapping(value = "Images/ViewEdit/{id}")
    @ResponseBody
    public ModelAndView getImagesViewEdit(@PathVariable(value = "id") Integer id, ModelMap mm, HttpSession session) {
        GaleryPagination pagination = (GaleryPagination) session.getAttribute("GALERY_IMAGES_PAGINATION");
        pagination.setEditViewName("/galery_images_edit_modal");
        session.setAttribute("GALERY_IMAGES_PAGINATION", pagination);
        Images img = (Images) new ImagesFacade().find(id);
        mm.put("IMAGES", img);
        if (img == null || img.getIsDelete()) {
            return null;
        }
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + pagination.getEditViewName());
    }
    
    @RequestMapping(value = "/Images/Edit", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView editImages(@RequestBody Map map, ModelMap mm, HttpSession session) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        try {
            Images images = new Images();
            images.setId(Integer.parseInt(map.get("id").toString()));
            images.setName(map.get("name") == null ? null : map.get("name").toString());
            images.setPath(map.get("path") == null ? null : map.get("path").toString());
            images.setIsShow(Boolean.valueOf(map.get("isShow") == null ? null : map.get("isShow").toString()));
            images.setOrderNumber(map.get("orderNumber") == null ? null : map.get("orderNumber") == null ? null : Integer.parseInt(map.get("orderNumber").toString()));
            images.setDescription(map.get("description") == null ? null : map.get("description").toString());
            images.setGaleryId(new Galery(Integer.parseInt(map.get("galeryId").toString())));
            images.setIsDelete(false);
            new ImagesFacade().edit(images);
        } catch (Exception ex) {
            ex.printStackTrace();
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Cập nhật ảnh thành công!");
        mm.put("MESSAGE_PAGINATION", mP);
        return mAV;
    }
    
    @RequestMapping(value = "/Images/Delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView deleteImage(@PathVariable("id") int id, HttpSession session, ModelMap mm) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        try {
            Images images = (Images) new ImagesFacade().find(id);
            if (images == null || images.getIsDelete()) {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Ảnh không tồn tại!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            images.setIsDelete(true);
            new ImagesFacade().edit(images);

            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Xóa Ảnh thành công");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        } catch (Exception ex) {
            ex.printStackTrace();
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
    }

    @RequestMapping(value = "/Images/Hide/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView hideImage(@PathVariable("id") int id, HttpSession session, ModelMap mm) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        try {
            Images images = (Images) new ImagesFacade().find(id);
            if (images == null || images.getIsDelete()) {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Ảnh không tồn tại!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            images.setIsShow(!images.getIsShow());
            new ImagesFacade().edit(images);

            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", images.getIsShow() ? "Hiện ảnh thành công" : "Ẩn ảnh thành công");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        } catch (Exception ex) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
    }
}
