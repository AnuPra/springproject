package com.bank.demo.services;

import java.util.ArrayList;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.bank.demo.dto.BalanceInq;
import com.bank.demo.dto.TopTransactingAcct;
import com.bank.demo.dto.TopTransactingFactory;
import com.bank.demo.dto.TopicEnum;

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
	    TopicEnum topic = TopicEnum.compute(cr.topic());
	    topTransactingFactory.addRecord(id, cnt, topic);
	}

	public void sendMessage(String key, String msg) {
	    kafkaTemplate.send("transaction-input", key, key+":"+msg);
	}
	
	@Autowired
	TopTransactingAcct acctWithMaxBalanceInq;
	
	
	
	
	
	
	@Autowired
	TopTransactingAcct acctWithMaxWithdrawalInq;
	
	public void addRecord(String id, Long cnt, TopicEnum topic) throws Exception {
		switch(topic) {
		case BALANCE: { acctWithMaxBalanceInq.addRecord(id, cnt); break; }
		case WITHDRAWAL: { acctWithMaxWithdrawalInq.addRecord(id, cnt); break; }
		default: throw new Exception("Invalid topic");
		}
	}
	
	public ArrayList<BalanceInq> getRecords(TopicEnum topic) throws Exception {
		switch(topic) {
		case BALANCE: return acctWithMaxBalanceInq.top();
		case WITHDRAWAL: return acctWithMaxWithdrawalInq.top();
		default: throw new Exception("Invalid topic");
		}
	}

	public ArrayList<BalanceInq> getTopRecords(TopicEnum topic) {
		return topTransactingFactory.getRecords(topic);
	}
}