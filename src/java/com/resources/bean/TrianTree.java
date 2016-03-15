package com.resources.bean;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TrianTree implements Serializable {

    @Id
    private Integer pos;
    private Integer parentPos;
    private String userName;
    private Date dateCreated;
    private int totalChildren;
    private int levelRank;

    public TrianTree() {
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getTotalChildren() {
        return totalChildren;
    }

    public void setTotalChildren(int totalChildren) {
        this.totalChildren = totalChildren;
    }

    public int getLevelRank() {
        return levelRank;
    }

    public void setLevelRank(int levelRank) {
        this.levelRank = levelRank;
    }

}
