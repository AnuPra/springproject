package com.bank.demo.dto;

import java.util.Comparator;

public class TxnComparator implements Comparator<BalanceInq>{
    
	@Override
	public int compare(BalanceInq arg0, BalanceInq arg1) {
		Long cnt1 = Long.valueOf(arg0.count());
		Long cnt2 = Long.valueOf(arg1.count());
		
		if (cnt1 < cnt2)	return 1;
	    else if (cnt1 > cnt2)	return -1;

		return 0;
    }
}