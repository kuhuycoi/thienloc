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
@Table(name = "News")
public class News implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",insertable = false,updatable = false)
    private Integer id;
    @Column(name = "[name]")
    private String name;
    @Column(name = "seoPermalink")
    private String seoPermalink;
    @Column(name = "seoDescription")
    private String seoDescription;
    @Column(name = "seoTitle")
    private String seoTitle;
    @Column(name = "seoKeyword")
    private String seoKeyword;
    @Column(name = "seoMeta")
    private String seoMeta;
    @Column(name = "tags")
    private String tags;
    @Column(name = "titleImg")
    private String titleImg;
    @Column(name = "isShow")
    private Boolean isShow;
    @Column(name = "isDelete", insertable = false)
    private Boolean isDelete;
    @Column(name = "isHot")
    private Boolean isHot;
    @Column(name = "[content]")
    private String content;
    @Column(name = "shortDescription")
    private String shortDescription;
    @Column(name = "[orderNumber]")
    private Integer orderNumber;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdDate", insertable = false, updatable = false)
    private Date createdDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createdAdm", updatable = false)
    private Admin createdAdm;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "caId")
    private NewsCategories caId;

    public News(int id) {
        this.id = id;
    }

    public News() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSeoPermalink() {
        return seoPermalink;
    }

    public void setSeoPermalink(String seoPermalink) {
        this.seoPermalink = seoPermalink;
    }

    public String getSeoDescription() {
        return seoDescription;
    }

    public void setSeoDescription(String seoDescription) {
        this.seoDescription = seoDescription;
    }

    public String getSeoTitle() {
        return seoTitle;
    }

    public void setSeoTitle(String seoTitle) {
        this.seoTitle = seoTitle;
    }

    public String getSeoKeyword() {
        return seoKeyword;
    }

    public void setSeoKeyword(String seoKeyword) {
        this.seoKeyword = seoKeyword;
    }

    public String getSeoMeta() {
        return seoMeta;
    }

    public void setSeoMeta(String seoMeta) {
        this.seoMeta = seoMeta;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTitleImg() {
        return titleImg;
    }

    public void setTitleImg(String titleImg) {
        this.titleImg = titleImg;
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

    public Boolean getIsHot() {
        return isHot;
    }

    public void setIsHot(Boolean isHot) {
        this.isHot = isHot;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public NewsCategories getCaId() {
        return caId;
    }

    public void setCaId(NewsCategories caId) {
        this.caId = caId;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
    
}