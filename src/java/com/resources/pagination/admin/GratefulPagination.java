package com.resources.pagination.admin;

import java.math.BigDecimal;
import java.util.Date;

public class GratefulPagination extends DefaultAdminPagination {

    private String grandController;
    private Boolean hasPinCode;
    private Integer agencyId;
    private Date startDate;
    private Date endDate;
    private BigDecimal totalAward;
    private final static String CHANGE_DAY = "/ChangeDay";
    private final static String INSERT = "/Insert";
    private final static String EXPORT = "/Export";

    public GratefulPagination(String grandController) {
        setViewTitle("Danh sách mã PIN");
        setOrderColmn("pinCode");
        setChildrenController("/Grateful");
        setViewName("/grateful_customer_list");
        this.grandController = grandController;
    }
    public GratefulPagination(String viewName, String grandController,String viewTitle,String orderColumn) {
        setOrderColmn(orderColumn);
        setViewTitle("Danh sách mã PIN");
        setChildrenController("/Grateful");
        setViewName(viewName);
        setViewTitle(viewTitle);
        this.grandController = grandController;
    }

    public String getCHANGE_DAY() {
        return CHANGE_DAY;
    }

    public String getINSERT() {
        return INSERT;
    }

    public String getEXPORT() {
        return EXPORT;
    }

    public String getGrandController() {
        return grandController;
    }

    public void setGrandController(String grandController) {
        this.grandController = grandController;
    }

    public Boolean getHasPinCode() {
        return hasPinCode;
    }

    public void setHasPinCode(Boolean hasPinCode) {
        this.hasPinCode = hasPinCode;
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

    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }

    public BigDecimal getTotalAward() {
        return totalAward;
    }

    public void setTotalAward(BigDecimal totalAward) {
        this.totalAward = totalAward;
    }

}
