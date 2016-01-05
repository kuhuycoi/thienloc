package com.resources.pagination.admin;

import java.util.Date;

public class HistoryPagination extends DefaultAdminPagination {

    public String getCHANGE_DAY() {
        return CHANGE_DAY;
    }

    public String getCHANGE_DATE() {
        return CHANGE_DATE;
    }

    public String getCHANGE_PIN_TYPE() {
        return CHANGE_PIN_TYPE;
    }

    public String getEXPORT() {
        return EXPORT;
    }

    public String getACTIVE() {
        return ACTIVE;
    }

    private String grandController;
    private final static String CHANGE_DATE = "/ChangeDate";
    private final static String CHANGE_DAY = "/ChangeDay";
    private final static String CHANGE_PIN_TYPE = "/ChangePinType";
    private final static String EXPORT = "/Export";
    private final static String ACTIVE = "/Active";
    private Date day = null;
    private Integer pinType = null;
    private Boolean accepted = null;
    private Date startDate;
    private Date endDate;

    private Integer agencyId = null;    

    public HistoryPagination(String viewTitle, String grandController, String viewName) {
        setViewTitle(viewTitle);
        setOrderColmn("dateCreated");
        setAsc(false);
        setViewName(viewName);
        setChildrenController("/History");
        this.grandController = grandController;
        setInsertViewName("/history_deposit_pv_modal");
    }

    public HistoryPagination(String viewTitle, String orderColumn, boolean asc, String grandController, String viewName) {
        setViewTitle(viewTitle);
        setOrderColmn(orderColumn);
        setAsc(asc);
        setViewName(viewName);
        setChildrenController("/History");
        this.grandController = grandController;
        setInsertViewName("/history_deposit_pv_modal");
    }

    public HistoryPagination(String viewTitle, String orderColumn, boolean asc, String grandController, String viewName, String insertViewName) {
        setViewTitle(viewTitle);
        setOrderColmn(orderColumn);
        setAsc(asc);
        setViewName(viewName);
        setChildrenController("/History");
        this.grandController = grandController;
        setInsertViewName(insertViewName);
    }

    public String getGrandController() {
        return grandController;
    }

    public void setGrandController(String grandController) {
        this.grandController = grandController;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Integer getPinType() {
        return pinType;
    }

    public void setPinType(Integer pinType) {
        this.pinType = pinType;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
