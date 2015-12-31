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
@Table(name = "[Admin]")
public class Admin implements java.io.Serializable {

    private Integer id;
    private String name;
    private String userName;
    private String password;
    private String image;
    private Boolean isDelete;
    private Boolean isActive;
    private Date createdOnUtc;
    private Boolean gender;
    private String mobile;
    private String email;
    private String address;
    private String billingAddress;
    private String taxCode;
    private RoleAdmin roleAdmID;
    private ProvincialAgencies provincialAgencyID;

    public Admin() {
    }

    public Admin(int id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID",insertable = false,updatable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "Username", updatable = false)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "IsDelete", insertable = false)
    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    @Column(name = "IsActive")
    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Column(name = "Image", insertable = false, updatable = false)
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Column(name = "Password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CreatedOnUtc", insertable = false, updatable = false)
    public Date getCreatedOnUtc() {
        return createdOnUtc;
    }

    public void setCreatedOnUtc(Date createdOnUtc) {
        this.createdOnUtc = createdOnUtc;
    }

    @Column(name = "Mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "Email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "Address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "BillingAddress")
    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    @Column(name = "TaxCode")
    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    @Column(name = "Gender")
    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RoleAdmID")
    public RoleAdmin getRoleAdmID() {
        return roleAdmID;
    }

    public void setRoleAdmID(RoleAdmin roleAdmID) {
        this.roleAdmID = roleAdmID;
    }  

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ProvincialAgencyID")
    public ProvincialAgencies getProvincialAgencyID() {
        return provincialAgencyID;
    }

    public void setProvincialAgencyID(ProvincialAgencies provincialAgencyID) {
        this.provincialAgencyID = provincialAgencyID;
    }
}
