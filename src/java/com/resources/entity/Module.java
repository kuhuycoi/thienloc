package com.resources.entity;

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
import org.hibernate.annotations.Type;

@Entity
@Table(name = "[Module]")
public class Module implements java.io.Serializable {

    private int id;
    private Module module;
    private String name;
    private String controller;
    private String icon;
    private String cssClass;
    private Boolean isShow;
    private Boolean isDeleted;
    private List<Module> modules;
    private List<ModuleInRole> moduleInRole;

    public Module() {
    }

    public Module(int id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ParentID",updatable = false)
    public Module getModule() {
        return this.module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    @Column(name = "Name")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "Controller", length = 150)
    public String getController() {
        return this.controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    @Column(name = "Icon", length = 50)
    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Column(name = "CssClass", length = 50)
    public String getCssClass() {
        return this.cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    @Column(name = "IsShow")
    public Boolean getIsShow() {
        return this.isShow;
    }

    public void setIsShow(Boolean isShow) {
        this.isShow = isShow;
    }

    @Column(name = "IsDeleted",insertable = false)
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "module")
    public List<Module> getModules() {
        return this.modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "moduleID")
    public List<ModuleInRole> getModuleInRole() {
        return moduleInRole;
    }

    public void setModuleInRole(List<ModuleInRole> moduleInRole) {
        this.moduleInRole = moduleInRole;
    }    
}
