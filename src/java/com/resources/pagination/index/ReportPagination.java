package com.resources.pagination.index;

import java.util.Calendar;

public class ReportPagination extends DefaultIndexPagination {

    private String grandController;
    private final static String CHANGE_MONTH = "/ChangeMonth";
    private final static String CHANGE_YEAR = "/ChangeYear";
    private final static String CHANGE_CUSTOMER = "/ChangeCustomer";
    private Integer month=-1;
    private Integer year=-1;
    private Integer awardType;
    private Integer level;
    private Integer selectedId;

    public ReportPagination(String viewTitle, String grandController, String viewName, Integer awardType) {
        setViewTitle(viewTitle);
        setOrderColmn("dateCreated");
        setAsc(false);
        setViewName(viewName);
        setChildrenController("/Report");
        this.grandController = grandController;
        Calendar c = Calendar.getInstance();
        month = c.get(Calendar.MONTH) + 1;
        year = c.get(Calendar.YEAR);
    }
    
    public String getCHANGE_MONTH() {
        return CHANGE_MONTH;
    }
    
    public String getCHANGE_CUSTOMER() {
        return CHANGE_CUSTOMER;
    }

    public String getCHANGE_YEAR() {
        return CHANGE_YEAR;
    }

    public String getGrandController() {
        return grandController;
    }

    public void setGrandController(String grandController) {
        this.grandController = grandController;
    }
    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getAwardType() {
        return awardType;
    }

    public void setAwardType(Integer awardType) {
        this.awardType = awardType;
    }

    public Integer getSelectedId() {
        return selectedId;
    }

    public void setSelectedId(Integer selectedId) {
        this.selectedId = selectedId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
