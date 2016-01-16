package com.resources.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Galery")
public class Galery implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "isVideoCategories")
    private Boolean isVideoCategories;
    @Column(name = "isShow")
    private Boolean isShow;
    @Column(name = "isDelete", insertable = false)
    private Boolean isDelete;
    @Column(name = "shortDescription")
    private String shortDescription;
    @Column(name = "orderNumber")
    private int orderNumber;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdDate", insertable = false, updatable = false)
    private Date createdDate;
    @Column(name = "titleImg")
    private String titleImg;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "createdAdm", updatable = false)
    private Admin createdAdm;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "galeryId")
    private List<Images> imagesList; 
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "galeryId")
    private List<Videos> videosList; 
    
    
    public Galery(Integer id) {
        this.id = id;
    }

    public Galery() {
    }
    
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

    public Boolean getIsVideoCategories() {
        return isVideoCategories;
    }

    public void setIsVideoCategories(Boolean isVideoCategories) {
        this.isVideoCategories = isVideoCategories;
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

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
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

    public List<Images> getImagesList() {
        return imagesList;
    }

    public void setImagesList(List<Images> imagesList) {
        this.imagesList = imagesList;
    }

    public List<Videos> getVideosList() {
        return videosList;
    }

    public void setVideosList(List<Videos> videosList) {
        this.videosList = videosList;
    }

    public String getTitleImg() {
        return titleImg;
    }

    public void setTitleImg(String titleImg) {
        this.titleImg = titleImg;
    }
}
