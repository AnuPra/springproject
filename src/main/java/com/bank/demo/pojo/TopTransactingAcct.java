package com.bank.demo.pojo;

import java.util.ArrayList;
import java.util.PriorityQueue;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.bank.demo.pojo.response.TopAccountsResponse;

@Component
@Qualifier("acctWithMaxBalanceInq,acctWithMaxWithdrawalInq")
public class TopTransactingAcct {
	
	final static Integer MAX_INQ_CNT=5;
	PriorityQueue<TopAccountsResponse> inq = new PriorityQueue<TopAccountsResponse>(MAX_INQ_CNT, new TxnComparator());
	
    public void addRecord(String id, Long cnt) {
        inq.offer(new TopAccountsResponse(id, cnt));
        System.out.println(id+"->"+cnt);
        if (inq.size()>MAX_INQ_CNT) {
        	inq.poll();
        }
    }
    
    public ArrayList<TopAccountsResponse> top(){
    	return new ArrayList<TopAccountsResponse>(inq);
    }
}
