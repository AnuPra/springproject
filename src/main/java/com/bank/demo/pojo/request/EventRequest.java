package com.bank.demo.pojo.request;

public class EventRequest {

	String acctId;
	String transactionType;
	
	public EventRequest(String acctId, String transactionType) {
		this.acctId = acctId;
		this.transactionType = transactionType;
	}
	
	public void setAcctId(String acctId) {
		this.acctId = acctId;
	}
	
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	
	public String getAcctId() {
		return this.acctId;
	}
	
	public String getTransactionType() {
		return this.transactionType;
	}
}