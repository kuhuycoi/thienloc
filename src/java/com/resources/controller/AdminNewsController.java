package com.resources.controller;

import com.resources.entity.Admin;
import com.resources.entity.News;
import com.resources.entity.NewsCategories;
import com.resources.facade.NewsCategoriesFacade;
import com.resources.facade.NewsFacade;
import com.resources.pagination.admin.DefaultAdminPagination;
import com.resources.pagination.admin.MessagePagination;
import com.resources.pagination.admin.NewsPagination;
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
@RequestMapping(value = "/Admin/News")
public class AdminNewsController {
    
    @RequestMapping(value = "/CategoryNews")
    @ResponseBody
    public ModelAndView getTreeCategory(ModelMap mm, HttpSession session) {
        NewsPagination pagination = (NewsPagination) session.getAttribute("NEWS_CATEGORY_PAGINATION");
        if (pagination == null) {
            pagination = new NewsPagination("/news_category", "/CategoryNews");
        }
        pagination.setViewTitle("Danh sách chuyên mục bài viết");
        session.setAttribute("NEWS_CATEGORY_PAGINATION", pagination);
        return new ModelAndView(DefaultAdminPagination.CONTAINER_FOLDER + pagination.getViewName());
    }
    
    @RequestMapping(value = "/CategoryNews/DisplayPerPage/{displayPerPage}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView displayPerPageForCategoryNewsView(@PathVariable("displayPerPage") int displayPerPage, HttpSession session) {
        NewsPagination pagination = (NewsPagination) session.getAttribute("NEWS_CATEGORY_PAGINATION");
        if (pagination != null) {
            pagination.setDisplayPerPage(displayPerPage);
        }
        return categoryNewsView(pagination, session);
    }
    
    @RequestMapping(value = "/CategoryNews/OrderData/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView orderByCategoryNewsView(@PathVariable("orderBy") String orderBy, ModelMap mm, HttpSession session) {
        NewsPagination pagination = (NewsPagination) session.getAttribute("NEWS_CATEGORY_PAGINATION");
        if (pagination != null) {
            if (pagination.getOrderColmn().equals(orderBy)) {
                pagination.setAsc(!pagination.isAsc());
            }
            pagination.setOrderColmn(orderBy);
        }
        return categoryNewsView(pagination, session);
    }
    
    @RequestMapping(value = "/CategoryNews/GoTo/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoCategoryNewsView(@PathVariable("page") int page, HttpSession session) {
        NewsPagination pagination = (NewsPagination) session.getAttribute("NEWS_CATEGORY_PAGINATION");
        if (pagination != null) {
            pagination.setCurrentPage(page);
        }
        return categoryNewsView(pagination, session);
    }
    
    @RequestMapping(value = "/CategoryNews/Search", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public ModelAndView searchCategoryNewsView(@RequestBody Map map, HttpSession session) {
        String searchString = (String) map.get("searchString");
        if (StringUtils.isEmpty(searchString)) {
            return categoryNewsView(null, session);
        }
        List<String> keywords = (List) map.get("keywords");
        NewsPagination pagination = new NewsPagination("/news_category", "/CategoryNews");
        pagination.setSearchString(searchString);
        pagination.setKeywords(keywords);
        pagination.setViewTitle("Danh sách chuyên mục bài viết");
        return categoryNewsView(pagination, session);
    }
    
    private ModelAndView categoryNewsView(NewsPagination pagination, HttpSession session) {
        if (pagination == null) {
            pagination = new NewsPagination("/news_category", "/CategoryNews");
            pagination.setViewTitle("Danh sách chuyên mục bài viết");
        }
        new NewsCategoriesFacade().pageData(pagination);
        session.setAttribute("NEWS_CATEGORY_PAGINATION", pagination);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + pagination.getViewName());
    }
    
    @RequestMapping(value = "/CategoryNews/ViewInsert")
    @ResponseBody
    public ModelAndView getCategoryNewsViewInsert(ModelMap mm, HttpSession session) {
        NewsPagination pagination = (NewsPagination) session.getAttribute("NEWS_CATEGORY_PAGINATION");
        pagination.setInsertViewName("/news_category_insert_modal");
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + pagination.getInsertViewName());
    }
    
    @RequestMapping(value = "/CategoryNews/Insert", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView insertCategoryNews(@RequestBody NewsCategories newsCategory, ModelMap mm, HttpSession session) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        try {
            newsCategory.setCreatedAdm(new Admin((Integer) session.getAttribute("ADMIN_ID")));
            new NewsCategoriesFacade().create(newsCategory);
        } catch (Exception ex) {
            ex.printStackTrace();
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Thêm mới chuyên mục thành công");
        mm.put("MESSAGE_PAGINATION", mP);
        return mAV;
    }
    
    @RequestMapping(value = "/CategoryNews/ViewEdit/{id}")
    @ResponseBody
    public ModelAndView getCategoryNewsViewEdit(@PathVariable(value = "id") Integer id, ModelMap mm, HttpSession session) {
        NewsPagination pagination = (NewsPagination) session.getAttribute("NEWS_CATEGORY_PAGINATION");
        pagination.setEditViewName("/news_category_edit_modal");
        NewsCategories newsCategory = (NewsCategories) new NewsCategoriesFacade().find(id);
        mm.put("NEWS_CATEGORY_EDIT", newsCategory);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + pagination.getEditViewName());
    }
    
    @RequestMapping(value = "/CategoryNews/Edit", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView editCategoryNews(@RequestBody NewsCategories newsCategory, ModelMap mm, HttpSession session) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        try {
            newsCategory.setIsDelete(((NewsCategories) new NewsCategoriesFacade().find(newsCategory.getId())).getIsDelete());
            new NewsCategoriesFacade().edit(newsCategory);
        } catch (Exception ex) {
            ex.printStackTrace();
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Cập nhật chuyên mục thành công");
        mm.put("MESSAGE_PAGINATION", mP);
        return mAV;
    }
    
    @RequestMapping(value = "/CategoryNews/Hide/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView hideCategoryNews(@PathVariable("id") int id, HttpSession session, ModelMap mm) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        try {
            NewsCategories newsCategory = (NewsCategories) new NewsCategoriesFacade().find(id);
            if (newsCategory == null || newsCategory.getIsDelete()) {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Chuyên mục không tồn tại!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            newsCategory.setIsShow(!newsCategory.getIsShow());
            new NewsCategoriesFacade().edit(newsCategory);
            
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", newsCategory.getIsShow() ? "Hiện chuyên mục thành công" : "Ẩn chuyên mục thành công");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        } catch (Exception ex) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
    }
    
    @RequestMapping(value = "/CategoryNews/HideOnMenu/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView hideOnMenuCategoryNews(@PathVariable("id") int id, HttpSession session, ModelMap mm) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        try {
            NewsCategories newsCategory = (NewsCategories) new NewsCategoriesFacade().find(id);
            if (newsCategory == null || newsCategory.getIsDelete()) {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Chuyên mục không tồn tại!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            newsCategory.setIsShowOnMenu(!newsCategory.getIsShowOnMenu());
            new NewsCategoriesFacade().edit(newsCategory);
            
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", newsCategory.getIsShowOnMenu() ? "Hiện chuyên mục trên menu thành công" : "Ẩn chuyên mục trên menu thành công");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        } catch (Exception ex) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
    }
    
    @RequestMapping(value = "/CategoryNews/Delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView deleteCategoryNews(@PathVariable("id") int id, HttpSession session, ModelMap mm) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        try {
            NewsCategories newsCategory = (NewsCategories) new NewsCategoriesFacade().find(id);
            if (newsCategory == null || newsCategory.getIsDelete()) {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Chuyên mục không tồn tại!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            newsCategory.setIsDelete(Boolean.TRUE);
            new NewsCategoriesFacade().edit(newsCategory);
            
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Xóa chuyên mục thành công");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        } catch (Exception ex) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
    }

    // ListPost
    @RequestMapping(value = "/ListPost")
    @ResponseBody
    public ModelAndView getListPost(ModelMap mm, HttpSession session) {
        NewsPagination pagination = (NewsPagination) session.getAttribute("LIST_POST_PAGINATION");
        if (pagination == null) {
            pagination = new NewsPagination("/news_list_post", "/ListPost");
        }
        pagination.setViewTitle("Danh sách bài viết");
        session.setAttribute("LIST_POST_PAGINATION", pagination);
        return new ModelAndView(DefaultAdminPagination.CONTAINER_FOLDER + pagination.getViewName());
    }
    
    @RequestMapping(value = "ListPost/ViewInsert")
    @ResponseBody
    public ModelAndView getListPostViewInsert(ModelMap mm, HttpSession session) {
        NewsPagination pagination = (NewsPagination) session.getAttribute("LIST_POST_PAGINATION");
        pagination.setInsertViewName("/news_list_post_insert_modal");
        session.setAttribute("LIST_POST_PAGINATION", pagination);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + pagination.getInsertViewName());
    }
    
    @RequestMapping(value = "ListPost/ViewEdit/{id}")
    @ResponseBody
    public ModelAndView getListPostViewEdit(@PathVariable(value = "id") Integer id, ModelMap mm, HttpSession session) {
        NewsPagination pagination = (NewsPagination) session.getAttribute("LIST_POST_PAGINATION");
        pagination.setEditViewName("/news_list_post_edit_modal");
        session.setAttribute("LIST_POST_PAGINATION", pagination);
        News news = (News) new NewsFacade().find(id);
        mm.put("NEWS", news);
        if (news == null || news.getIsDelete()) {
            return null;
        }
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + pagination.getEditViewName());
    }
    
    @RequestMapping(value = "/ListPost/Insert", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView insertNews(@RequestBody Map map, ModelMap mm, HttpSession session) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        try {
            News news = new News();
            news.setName(map.get("name") == null ? null : map.get("name").toString());
            news.setSeoPermalink(map.get("seoPermalink") == null ? null : map.get("seoPermalink").toString());
            news.setSeoDescription(map.get("seoDescription") == null ? null : map.get("seoDescription").toString());
            news.setSeoTitle(map.get("seoTitle") == null ? null : map.get("seoTitle").toString());
            news.setSeoKeyword(map.get("seoKeyword") == null ? null : map.get("seoKeyword").toString());
            news.setSeoMeta(map.get("seoMeta") == null ? null : map.get("seoMeta").toString());
            news.setTags(map.get("tags") == null ? null : map.get("tags").toString());
            news.setTitleImg(map.get("titleImg") == null ? null : map.get("titleImg").toString());
            news.setCaId(map.get("caId") == null ? null : map.get("caId") == null ? null : new NewsCategories(Integer.parseInt(map.get("caId").toString())));
            news.setIsShow(Boolean.valueOf(map.get("isShow") == null ? null : map.get("isShow").toString()));
            news.setIsHot(Boolean.valueOf(map.get("isHot") == null ? null : map.get("isHot").toString()));
            news.setOrderNumber(map.get("orderNumber") == null ? null : map.get("orderNumber") == null ? null : Integer.parseInt(map.get("orderNumber").toString()));
            news.setShortDescription(map.get("shortDescription") == null ? null : map.get("shortDescription").toString());
            news.setContent(map.get("content") == null ? null : map.get("content").toString());
            news.setCreatedAdm(new Admin(Integer.parseInt(session.getAttribute("ADMIN_ID").toString())));
            new NewsFacade().create(news);
        } catch (Exception ex) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Thêm mới tin tức thành công");
        mm.put("MESSAGE_PAGINATION", mP);
        return mAV;
    }
    
    @RequestMapping(value = "/ListPost/Update", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView updateNews(@RequestBody Map map, ModelMap mm, HttpSession session) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        try {
            News news = new News();
            news.setId(map.get("id") == null ? null : Integer.parseInt(map.get("id").toString()));
            news.setName(map.get("name") == null ? null : map.get("name").toString());
            news.setSeoPermalink(map.get("seoPermalink") == null ? null : map.get("seoPermalink").toString());
            news.setSeoDescription(map.get("seoDescription") == null ? null : map.get("seoDescription").toString());
            news.setSeoTitle(map.get("seoTitle") == null ? null : map.get("seoTitle").toString());
            news.setSeoKeyword(map.get("seoKeyword") == null ? null : map.get("seoKeyword").toString());
            news.setSeoMeta(map.get("seoMeta") == null ? null : map.get("seoMeta").toString());
            news.setTags(map.get("tags") == null ? null : map.get("tags").toString());
            news.setTitleImg(map.get("titleImg") == null ? null : map.get("titleImg").toString());
            news.setCaId(map.get("caId") == null ? null : map.get("caId") == null ? null : new NewsCategories(Integer.parseInt(map.get("caId").toString())));
            news.setIsShow(Boolean.valueOf(map.get("isShow") == null ? null : map.get("isShow").toString()));
            news.setIsHot(Boolean.valueOf(map.get("isHot") == null ? null : map.get("isHot").toString()));
            news.setOrderNumber(map.get("orderNumber") == null ? null : map.get("orderNumber") == null ? null : Integer.parseInt(map.get("orderNumber").toString()));
            news.setShortDescription(map.get("shortDescription") == null ? null : map.get("shortDescription").toString());
            news.setContent(map.get("content") == null ? "" : map.get("content").toString());
            news.setIsDelete(map.get("isDelete") == null ? null : Boolean.valueOf(map.get("isDelete").toString()));
            new NewsFacade().edit(news);
        } catch (Exception ex) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
        mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Cập nhật tin tức thành công");
        mm.put("MESSAGE_PAGINATION", mP);
        return mAV;
    }
    
    @RequestMapping(value = "/ListPost/DisplayPerPage/{displayPerPage}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView displayPerPageForListPostView(@PathVariable("displayPerPage") int displayPerPage, HttpSession session) {
        NewsPagination pagination = (NewsPagination) session.getAttribute("LIST_POST_PAGINATION");
        if (pagination != null) {
            pagination.setDisplayPerPage(displayPerPage);
        }
        return listPostView(pagination, session);
    }
    
    @RequestMapping(value = "/ListPost/OrderData/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView orderByListPostView(@PathVariable("orderBy") String orderBy, ModelMap mm, HttpSession session) {
        NewsPagination pagination = (NewsPagination) session.getAttribute("LIST_POST_PAGINATION");
        if (pagination != null) {
            if (pagination.getOrderColmn().equals(orderBy)) {
                pagination.setAsc(!pagination.isAsc());
            }
            pagination.setOrderColmn(orderBy);
        }
        return listPostView(pagination, session);
    }
    
    @RequestMapping(value = "/ListPost/GoTo/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoListPostView(@PathVariable("page") int page, HttpSession session) {
        NewsPagination pagination = (NewsPagination) session.getAttribute("LIST_POST_PAGINATION");
        if (pagination != null) {
            pagination.setCurrentPage(page);
        }
        return listPostView(pagination, session);
    }
    
    @RequestMapping(value = "/ListPost/Search", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public ModelAndView searchListPostView(@RequestBody Map map, HttpSession session) {
        String searchString = (String) map.get("searchString");
        if (StringUtils.isEmpty(searchString)) {
            return listPostView(null, session);
        }
        List<String> keywords = (List) map.get("keywords");
        NewsPagination pagination = new NewsPagination("/news_list_post", "/ListPost");
        pagination.setSearchString(searchString);
        pagination.setKeywords(keywords);
        return listPostView(pagination, session);
    }
    
    private ModelAndView listPostView(NewsPagination pagination, HttpSession session) {
        if (pagination == null) {
            pagination = new NewsPagination("/news_list_post", "/ListPost");
        }
        new NewsFacade().pageData(pagination);
        session.setAttribute("LIST_POST_PAGINATION", pagination);
        return new ModelAndView(DefaultAdminPagination.AJAX_FOLDER + pagination.getViewName());
    }
    
    @RequestMapping(value = "/ListPost/Delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView delete(@PathVariable("id") int id, HttpSession session, ModelMap mm) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        try {
            News news = (News) new NewsFacade().find(id);
            if (news == null || news.getIsDelete()) {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Tin tức không tồn tại!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            new NewsFacade().remove(news);
            
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", "Xóa tin tức thành công");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        } catch (Exception ex) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
    }
    
    @RequestMapping(value = "/ListPost/Hide/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView hide(@PathVariable("id") int id, HttpSession session, ModelMap mm) {
        ModelAndView mAV = new ModelAndView(DefaultAdminPagination.MESSAGE_FOLDER + MessagePagination.MESSAGE_VIEW);
        MessagePagination mP;
        try {
            News news = (News) new NewsFacade().find(id);
            if (news == null || news.getIsDelete()) {
                mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Tin tức không tồn tại!");
                mm.put("MESSAGE_PAGINATION", mP);
                return mAV;
            }
            news.setIsShow(!news.getIsShow());
            new NewsFacade().edit(news);
            
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_SUCCESS, "thành công", news.getIsShow() ? "Hiện tin tức thành công" : "Ẩn tin tức thành công");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        } catch (Exception ex) {
            mP = new MessagePagination(MessagePagination.MESSAGE_TYPE_ERROR, "Lỗi", "Đã xảy ra lỗi. Thử lại sau!");
            mm.put("MESSAGE_PAGINATION", mP);
            return mAV;
        }
    }
}
