package com.resources.pagination.admin;


public class AgencyPagination extends DefaultAdminPagination {

    private String grandController;
    
    public AgencyPagination(String viewName, String grandController) {
        setViewTitle("Danh sách mua gói đại lý");
        setOrderColmn("dateCreated");
        setAsc(false);
        setChildrenController("/Customer");
        setViewName(viewName);
        this.grandController=grandController;
    }

    public String getGrandController() {
        return grandController;
    }

    public void setGrandController(String grandController) {
        this.grandController = grandController;
    }
    
}
