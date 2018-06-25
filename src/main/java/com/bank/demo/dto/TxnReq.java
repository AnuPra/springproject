package com.bank.demo.dto;

public class TxnReq {
	String id;
	Double amt;
	
	public TxnReq(String id, Double amt) {
		this.id = id;
		this.amt = amt;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public void setAmt(Double amt) {
		this.amt = amt;
	}
	
	public Double getAmt() {
		return amt;
	}
}