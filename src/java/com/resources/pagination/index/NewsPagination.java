package com.resources.pagination.index;

public class NewsPagination extends DefaultIndexPagination {

    private String grandController;
    private Integer newsCategory;

    public NewsPagination(String viewTitle, String grandController, String viewName, Integer newsCategory) {
        setViewTitle(viewTitle);
        setOrderColmn("orderNumber");
        setAsc(true);
        setViewName(viewName);
        setChildrenController("/News");
        this.grandController = grandController;
        this.newsCategory = newsCategory;
    }

    public NewsPagination(String viewTitle, String viewName, Integer newsCategory) {
        setDisplayPerPage(7);
        setViewTitle(viewTitle);
        setOrderColmn("orderNumber");
        setAsc(true);
        setViewName(viewName);
        setChildrenController("/News");
        this.newsCategory = newsCategory;
    }

    public String getGrandController() {
        return grandController;
    }

    public void setGrandController(String grandController) {
        this.grandController = grandController;
    }

    public Integer getNewsCategory() {
        return newsCategory;
    }

    public void setNewsCategory(Integer newsCategory) {
        this.newsCategory = newsCategory;
    }
}
