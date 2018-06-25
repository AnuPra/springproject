package com.bank.demo.dto;

public enum TopicEnum {

	BALANCE("balance-output"),
	WITHDRAWAL("withdrawal-output");
	
	private String topic;
	
	TopicEnum(String topic) {
		this.topic = topic;
	}
	
	public static TopicEnum compute(String input) {
	
		for (TopicEnum val: TopicEnum.values()) {
			if (val.topic.equals(input))
				return val;
		}
		return null;
	}

}
