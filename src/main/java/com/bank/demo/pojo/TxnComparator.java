package com.bank.demo.pojo;

import java.util.Comparator;

import com.bank.demo.pojo.response.TopAccountsResponse;

public class TxnComparator implements Comparator<TopAccountsResponse>{
    
	@Override
	public int compare(TopAccountsResponse arg0, TopAccountsResponse arg1) {
		Long cnt1 = Long.valueOf(arg0.count());
		Long cnt2 = Long.valueOf(arg1.count());
		
		if (cnt1 < cnt2)	return 1;
	    else if (cnt1 > cnt2)	return -1;

		return 0;
    }
}