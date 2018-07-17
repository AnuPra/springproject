package com.bank.demo.services;

import java.util.ArrayList;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.bank.demo.pojo.TopTransactingAcct;
import com.bank.demo.pojo.TopTransactingFactory;
import com.bank.demo.pojo.response.TopAccountsResponse;
import com.bank.demo.pojo.enums.KafkaTopicEnum;

@Service
public class KafkaService {
	
	@Autowired
	TopTransactingFactory topTransactingFactory;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	private static final Logger logger = LoggerFactory.getLogger(KafkaService.class);
	
	@KafkaListener(topics = "balance-output", id="streams-wordcount1")
	public void listen(ConsumerRecord<?, ?> cr) throws Exception {
	    System.out.println("Received: "+cr.value()+":"+cr.key()+":"+cr.topic());
	    logger.debug("Received: "+cr.value()+":"+cr.key()+":"+cr.topic());
		
	    Long cnt = (Long) cr.value();
	    String id = (String) cr.key();
	    KafkaTopicEnum topic = KafkaTopicEnum.compute(cr.topic());
	    topTransactingFactory.addRecord(id, cnt, topic);
	}

	public void sendMessage(String key, String msg) {
	    kafkaTemplate.send("transaction-input", key, key+":"+msg);
	}

	public ArrayList<TopAccountsResponse> getTopRecords(KafkaTopicEnum topic) throws Exception {
		return topTransactingFactory.getRecords(topic);
	}
}