package com.bank.demo.pojo.response;

public class TopAccountsResponse implements Comparable<Long>{

	private String acctId;
	private String count;
	
	public TopAccountsResponse(String acctId, Long count) {
		this.acctId = acctId;
		this.count = count.toString();
	}
	
	public String getAcctId() {
		return this.acctId;
	}
	
	public String count() {
		return this.count.toString();
	}
	
	public void setAcctId(String acctId) {
		this.acctId = acctId;
	}
	
	public void setCount(Long count) {
		this.count = count.toString();
	}

	@Override
	public int compareTo(Long arg0) {
		return Double.compare(arg0, Long.parseLong(count));
	}
}