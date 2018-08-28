package com.bank.demo.services;

import java.util.ArrayList;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import com.bank.demo.pojo.enums.KafkaTopicEnum;
import com.bank.demo.pojo.response.TopAccountsResponse;

public interface IMessageService {

	public void listen(ConsumerRecord<?, ?> cr) throws Exception;
	
	public void sendMessage(String key, String msg);
	
	public ArrayList<TopAccountsResponse> getTopRecords(KafkaTopicEnum topic) throws Exception;
} 