package com.bank.demo.pojo.request;

public class TransactionRequest {
	long id;
	Double amt;
	
	public TransactionRequest(long id, Double amt) {
		this.id = id;
		this.amt = amt;
	}
	
	public TransactionRequest(long id) {
		this.id = id;
	}
	
	public TransactionRequest() {}
	
	public long getId() {
		return id;
	}

	public String getIdAsString() {
		return String.valueOf(id);
	}

	public void setAmt(Double amt) {
		this.amt = amt;
	}
	
	public Double getAmt() {
		return amt;
	}
}