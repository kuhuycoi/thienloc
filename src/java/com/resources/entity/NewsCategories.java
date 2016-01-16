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
@Table(name = "News_Categories")
public class NewsCategories implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "isShow")
    private Boolean isShow;
    @Column(name = "isDelete", insertable = false)
    private Boolean isDelete;
    @Column(name = "isShowOnMenu")
    private Boolean isShowOnMenu;
    @Column(name = "description")
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdDate", insertable = false, updatable = false)
    private Date createdDate;
    @Column(name = "[level]")
    private Integer level;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createdAdm", updatable = false)
    private Admin createdAdm;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "caId")
    private List<News> news;

    public NewsCategories() {
    }

    public NewsCategories(Integer id) {
        this.id = id;
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

    public Boolean getIsShowOnMenu() {
        return isShowOnMenu;
    }

    public void setIsShowOnMenu(Boolean isShowOnMenu) {
        this.isShowOnMenu = isShowOnMenu;
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

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

}
