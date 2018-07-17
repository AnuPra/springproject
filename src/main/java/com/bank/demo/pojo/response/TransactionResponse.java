package com.bank.demo.pojo.response;

public class TransactionResponse {
	long id;
	Double amt;
	
	public TransactionResponse(long id, Double amt) {
		this.id = id;
		this.amt = amt;
	}
	
	public TransactionResponse(Double amt) {
		this.amt = amt;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	
	public void setAmt(Double amt) {
		this.amt = amt;
	}
	
	public Double getAmt() {
		return amt;
	}
}