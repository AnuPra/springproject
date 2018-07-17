package com.bank.demo.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Table(name = "account")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"})
@Entity
public class Account implements Serializable {
    
	private static final long serialVersionUID = -7503723518668086115L;

	public Account(Double amt) {
		this.amt = amt;
	}
	
	public Account() {}
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long acctId;

	@Column(nullable=false)
    private Double amt;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updatedAt;
    
    public String getAmtAsString() {
    	return amt.toString();
    }
    
    public Double getAmt() {
    	return amt;
    }
   
    public void setAmt(Double amt) {
    	this.amt = amt; 
    }
    
    public Long getAcctId() {
    	return this.acctId;
    }
}