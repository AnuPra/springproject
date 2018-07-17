package com.bank.demo.pojo;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bank.demo.pojo.enums.KafkaTopicEnum;
import com.bank.demo.pojo.exceptions.TopicNotFoundException;
import com.bank.demo.pojo.response.TopAccountsResponse;

@Component
public class TopTransactingFactory {

	@Autowired
	TopTransactingAcct acctWithMaxBalanceInq;
	
	@Autowired
	TopTransactingAcct acctWithMaxWithdrawalInq;
	
	public void addRecord(String id, Long cnt, KafkaTopicEnum topic) throws Exception {
		switch(topic) {
		case BALANCE: { acctWithMaxBalanceInq.addRecord(id, cnt); break; }
		case WITHDRAWAL: { acctWithMaxWithdrawalInq.addRecord(id, cnt); break; }
		default: throw new TopicNotFoundException();
		}
	}
	
	public ArrayList<TopAccountsResponse> getRecords(KafkaTopicEnum topic) throws Exception {
		switch(topic) {
		case BALANCE: return acctWithMaxBalanceInq.top();
		case WITHDRAWAL: return acctWithMaxWithdrawalInq.top();
		default: throw new TopicNotFoundException();
		}
	}
}