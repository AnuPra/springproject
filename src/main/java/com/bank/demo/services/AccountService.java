package com.bank.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.bank.demo.data.Account;
import com.bank.demo.data.AccountRepository;

@Service
public class AccountService {

	@Autowired 
	AccountRepository accountRepository;
	
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public String getBalance(Long acct_id) {
		
		Optional<Account> account = accountRepository.findById(acct_id);
		
		if (account.isPresent()) {
			return account.get().getAmtAsString();
		} else {
			return "Account not found.";
		}
	}
	
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public String withdraw(Long acctId, Double amt) {
		
		Optional<Account> account = accountRepository.findById(acctId);
		
		if (account.isPresent()) {
			if (account.get().getAmt() > amt) {
				Double newBalance = account.get().getAmt() - amt;
				accountRepository.updateBalance(acctId, newBalance);
				return "Successful withdrawal";
			}
			return "Insufficient Balance";
		} else {
			return "Account not found.";
		}
	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	public String deposit(Long acctId, Double amt) {
	
		Optional<Account> account = accountRepository.findById(acctId);
		
		if (account.isPresent()) {
			Double newBalance = account.get().getAmt() + amt;
			accountRepository.updateBalance(acctId, newBalance);
			return "Successful Deposit";
		} else {
			return "Account not found.";
		}
}
}
