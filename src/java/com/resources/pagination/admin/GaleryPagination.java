package com.resources.pagination.admin;


public class GaleryPagination extends DefaultAdminPagination {

    private String grandController;
    private static String HIDE="/Hide";
    
    public GaleryPagination(String viewName, String grandController) {
        setViewTitle("Danh sách Album");
        setOrderColmn("id");
        setChildrenController("/Galery");
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
