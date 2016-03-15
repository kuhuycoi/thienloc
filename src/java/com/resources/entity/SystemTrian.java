package com.resources.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "SystemTrian")
public class SystemTrian implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerID")
    private Customer customerID;

    @Column(name = "pos")
    private Integer pos;

    @Column(name = "ParentPos")
    private Integer parentPos;

    @Column(name = "Levetree")
    private Integer levetree;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DateCreated", length = 23, insertable = false, updatable = false)
    private Date dateCreated;
    
    @Column(name = "TotalChildren")
    private Integer totalChildren;
    
    @Column(name = "LevelRank")
    private Integer levelRank;

    public SystemTrian() {
    }

    public SystemTrian(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Customer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Customer customerID) {
        this.customerID = customerID;
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
