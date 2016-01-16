package com.resources.pagination.admin;


public class NewsPagination extends DefaultAdminPagination {

    private String grandController;
    private static String HIDE="/Hide";
    
    public NewsPagination(String viewName, String grandController) {
        setOrderColmn("id");
        setChildrenController("/News");
        setViewName(viewName);
        this.grandController=grandController;
    }
    public String getGrandController() {
        return grandController;
    }

    public void setGrandController(String grandController) {
        this.grandController = grandController;
    }

    public String getHIDE() {
        return HIDE;
    }
}
