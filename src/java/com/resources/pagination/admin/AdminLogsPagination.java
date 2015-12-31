package com.resources.pagination.admin;


public class AdminLogsPagination extends DefaultAdminPagination {

    private String grandController;
    
    public AdminLogsPagination(String viewName, String grandController) {
        setViewTitle("Activity Logs");
        setOrderColmn("importedDate");
        setAsc(false);
        setChildrenController("/AdminLogs");
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
