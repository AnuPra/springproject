package com.bank.demo.dto;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopTransactingFactory {

	@Autowired
	TopTransactingAcct acctWithMaxBalanceInq;
	
	@Autowired
	TopTransactingAcct acctWithMaxWithdrawalInq;
	
	public void addRecord(String id, Long cnt, TopicEnum topic) {
		switch(topic) {
		case BALANCE: { acctWithMaxBalanceInq.addRecord(id, cnt); break; }
		case WITHDRAWAL: { acctWithMaxWithdrawalInq.addRecord(id, cnt); break; }
		default: break;
		}
	}
	
	public ArrayList<BalanceInq> getRecords(TopicEnum topic) {
		switch(topic) {
		case BALANCE: return acctWithMaxBalanceInq.top();
		case WITHDRAWAL: return acctWithMaxWithdrawalInq.top();
		default: return new ArrayList<BalanceInq>();
		}
	}
}