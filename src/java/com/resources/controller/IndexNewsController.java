package com.resources.controller;

import com.resources.entity.News;
import com.resources.entity.NewsCategories;
import com.resources.facade.NewsCategoriesFacade;
import com.resources.facade.NewsFacade;
import com.resources.pagination.index.DefaultIndexPagination;
import com.resources.pagination.index.NewsPagination;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = {"/News", "/tin-tuc"})
public class IndexNewsController {

    @RequestMapping(value = "/{id}/trang-{page}")
    public ModelAndView getNewsView(@PathVariable(value = "id") Integer id,
            @PathVariable(value = "page") Integer page,
            ModelMap mm, HttpSession session) {

        NewsCategories category = (NewsCategories) new NewsCategoriesFacade().find(id);
        NewsPagination newsPagination = (NewsPagination) session.getAttribute("INDEX_NEWS_PAGINATION");
        if (newsPagination == null) {
            newsPagination = new NewsPagination(category.getName(), "/news", id);
            newsPagination.setDisplayPerPage(12);
        }
        newsPagination.setCurrentPage(page);
        newsPagination.setNewsCategory(category.getId());
        new NewsFacade().getListPost(newsPagination);
        session.setAttribute("INDEX_NEWS_PAGINATION", newsPagination);
        mm.put("INDEX_NEWS_CATEGORY", category);
        return new ModelAndView(newsPagination.getViewName());
    }

    @RequestMapping(value = "/{caId}/{name}")
    public ModelAndView getNewsView(@PathVariable(value = "caId") Integer caId, @PathVariable(value = "name") String name, ModelMap mm, HttpSession session) {
        News news = (News) new NewsFacade().findByName(caId, name);
        session.setAttribute("INDEX_NEWS", news);
        return new ModelAndView("/news_detail");
    }

    @RequestMapping(value = "/Detail/{id}")
    public ModelAndView getNewsDetailView(@PathVariable(value = "id") Integer id, ModelMap mm, HttpSession session) {
        session.setAttribute("INDEX_NEWS_DETAIL", new NewsFacade().find(id));
        return new ModelAndView("/news_detail");
    }

    private ModelAndView newsView(NewsPagination newsPagination, HttpSession session) {
        new NewsFacade().pageData(newsPagination);
        session.setAttribute("INDEX_NEWS", newsPagination);
        return new ModelAndView(DefaultIndexPagination.AJAX_FOLDER + newsPagination.getViewName());
    }
}
