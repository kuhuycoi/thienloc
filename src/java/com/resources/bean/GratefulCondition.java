package com.resources.bean;

import java.io.Serializable;

public class GratefulCondition implements Serializable{
    private String activeStartDate;
    private String activeFinishDate;
    private String joinGratefulDate;

    public String getActiveStartDate() {
        return activeStartDate;
    }

    public void setActiveStartDate(String activeStartDate) {
        this.activeStartDate = activeStartDate;
    }

    public String getActiveFinishDate() {
        return activeFinishDate;
    }

    public void setActiveFinishDate(String activeFinishDate) {
        this.activeFinishDate = activeFinishDate;
    }

    public String getJoinGratefulDate() {
        return joinGratefulDate;
    }

    public void setJoinGratefulDate(String joinGratefulDate) {
        this.joinGratefulDate = joinGratefulDate;
    }
}
