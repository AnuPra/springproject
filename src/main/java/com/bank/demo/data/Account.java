package com.bank.demo.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;

@Table(name = "account")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"})
@Entity
public class Account implements Serializable {
    
	private static final long serialVersionUID = -7503723518668086115L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long acctId;

    @NotBlank
    private Double amt;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
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
}