package com.bank.demo.pojo.request;

public class TransactionRequest {
	long id;
	Double amt;
	
	public TransactionRequest(long id, Double amt) {
		this.id = id;
		this.amt = amt;
	}
	
	public TransactionRequest(Double amt) {
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