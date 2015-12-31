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
@Table(name = "Promotion")
public class Promotion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", insertable = false, updatable = false)
    private Integer id;    
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CustomerId")
    private Customer customerId;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RankAgencyID")
    private RankCustomes rankAgencyID;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "DateCreated", insertable = false, updatable = false)
    private Date dateCreated;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "DateEnd", insertable = false, updatable = false)
    private Date dateEnd;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }

    public RankCustomes getRankAgencyID() {
        return rankAgencyID;
    }

    public void setRankAgencyID(RankCustomes rankAgencyID) {
        this.rankAgencyID = rankAgencyID;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }
}
