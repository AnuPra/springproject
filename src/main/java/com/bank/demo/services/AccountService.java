package com.bank.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.bank.demo.data.*;
import com.bank.demo.pojo.exceptions.AccountNotFoundException;
import com.bank.demo.pojo.request.EventRequest;

@Service
public class AccountService {

	@Autowired 
	AccountRepository accountRepository;
	
	private final RestTemplate restTemplate;

	public AccountService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	public void invokeRestCall(String acctId, String topic) {
		EventRequest msg = new EventRequest(acctId, topic);
		restTemplate.postForObject("/createLog", msg, EventRequest.class);
	}

	
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public Double getBalance(Long acct_id) {
		
		Optional<Account> account = accountRepository.findById(acct_id);
		
		if (account.isPresent()) {
			return account.get().getAmt();
		}
		throw new AccountNotFoundException();
	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	public Long create(Double amt) {
		
		Account acct = new Account(amt);
		Account result = accountRepository.save(acct);
		return result.getAcctId();
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
		} 
		throw new AccountNotFoundException();
	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	public String deposit(Long acctId, Double amt) {
	
		Optional<Account> account = accountRepository.findById(acctId);
		
		if (account.isPresent()) {
			Double newBalance = account.get().getAmt() + amt;
			accountRepository.updateBalance(acctId, newBalance);
			return "Successful Deposit";
		} 
		throw new AccountNotFoundException();
	}
}
