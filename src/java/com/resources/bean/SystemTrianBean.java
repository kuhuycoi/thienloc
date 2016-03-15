package com.resources.bean;

import java.util.Date;
import javax.persistence.Id;

public class SystemTrianBean implements java.io.Serializable {

    @Id
    private Integer id;
    private String customerUserName;
    private Integer pos;
    private Integer parentPos;
    private Integer levetree;
    private Date dateCreated;
    private Integer totalChildren;
    private Integer levelRank;

    public SystemTrianBean() {
    }

    public SystemTrianBean(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }

    public Integer getParentPos() {
        return parentPos;
    }

    public void setParentPos(Integer parentPos) {
        this.parentPos = parentPos;
    }

    public Integer getLevetree() {
        return levetree;
    }

    public void setLevetree(Integer levetree) {
        this.levetree = levetree;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getCustomerUserName() {
        return customerUserName;
    }

    public void setCustomerUserName(String customerUserName) {
        this.customerUserName = customerUserName;
    }

    public Integer getTotalChildren() {
        return totalChildren;
    }

    public void setTotalChildren(Integer totalChildren) {
        this.totalChildren = totalChildren;
    }

    public Integer getLevelRank() {
        return levelRank;
    }

    public void setLevelRank(Integer levelRank) {
        this.levelRank = levelRank;
    }

}
