package com.resources.entity;

import java.io.Serializable;
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
@Table(name = "Images")
public class Images implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "[path]")
    private String path;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "galeryId")
    private Galery galeryId;
    @Column(name = "isShow")
    private Boolean isShow;
    @Column(name = "isDelete", insertable = false)
    private Boolean isDelete;
    @Column(name = "orderNumber")
    private int orderNumber;
    @Column(name = "description")
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdDate", insertable = false, updatable = false)
    private Date createdDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "createdAdm", updatable = false)
    private Admin createdAdm;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Galery getGaleryId() {
        return galeryId;
    }

    public void setGaleryId(Galery galeryId) {
        this.galeryId = galeryId;
    }

    public Boolean getIsShow() {
        return isShow;
    }

    public void setIsShow(Boolean isShow) {
        this.isShow = isShow;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Admin getCreatedAdm() {
        return createdAdm;
    }

    public void setCreatedAdm(Admin createdAdm) {
        this.createdAdm = createdAdm;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }
}
