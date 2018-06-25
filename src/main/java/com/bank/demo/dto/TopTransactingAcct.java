package com.bank.demo.dto;

import java.util.ArrayList;
import java.util.PriorityQueue;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("acctWithMaxBalanceInq,acctWithMaxWithdrawalInq")
public class TopTransactingAcct {
	
	final static Integer MAX_INQ_CNT=5;
	PriorityQueue<BalanceInq> inq = new PriorityQueue<BalanceInq>(MAX_INQ_CNT, new TxnComparator());
	
    public void addRecord(String id, Long cnt) {
        inq.offer(new BalanceInq(id, cnt));
        System.out.println(id+"->"+cnt);
        if (inq.size()>MAX_INQ_CNT) {
        	inq.poll();
        }
    }
    
    public ArrayList<BalanceInq> top(){
    	return new ArrayList<BalanceInq>(inq);
    }
}
