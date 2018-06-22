package com.bank.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.demo.data.Account;
import com.bank.demo.data.AccountRepository;

@Service
public class AccountService {

	@Autowired 
	AccountRepository accountRepository;
	
	public String getBalance(Long acct_id) {
		
		Optional<Account> account = accountRepository.findById(acct_id);
		
		if (account.isPresent()) {
			return account.get().getAmtAsString();
		} else {
			return "Account not found.";
		}
	}
}
