package com.bank.demo.pojo.enums;

public enum KafkaTopicEnum {

	BALANCE("balance-output"),
	WITHDRAWAL("withdrawal-output");
	
	private String topic;
	
	KafkaTopicEnum(String topic) {
		this.topic = topic;
	}
	
	public static KafkaTopicEnum compute(String input) {
	
		for (KafkaTopicEnum val: KafkaTopicEnum.values()) {
			if (val.topic.equals(input))
				return val;
		}
		return null;
	}

}
