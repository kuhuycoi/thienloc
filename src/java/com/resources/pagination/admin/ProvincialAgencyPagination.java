package com.resources.pagination.admin;


public class ProvincialAgencyPagination extends DefaultAdminPagination {
    private String grandController;
    
    public ProvincialAgencyPagination(String viewName, String grandController) {
        setViewTitle("Danh sách đại lý");
        setOrderColmn("id");
        setChildrenController("/Customer");
        setInsertViewName("/customer_provincial_agencies_insert_modal");
        setEditViewName("/customer_provincial_agencies_edit_modal");
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
