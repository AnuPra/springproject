package com.bank.demo.pojo.response;

public class StatusResponse {

	private String msg;
	
	public StatusResponse(String msg) {
		this.msg = msg;
	}
	
	public String getStatus() {
		return this.msg;
	}
}
