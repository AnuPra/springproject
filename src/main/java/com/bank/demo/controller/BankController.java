package com.bank.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.bank.demo.pojo.enums.KafkaTopicEnum;
import com.bank.demo.pojo.request.TransactionRequest;
import com.bank.demo.pojo.response.StatusResponse;
import com.bank.demo.pojo.response.TopAccountsResponse;
import com.bank.demo.pojo.response.TransactionResponse;
import com.bank.demo.services.AccountService;
import com.bank.demo.services.IMessageService;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 
 * @author anusha pratty
 * Bank Controller to handle requests for banking operations such as account creation, balance check, withdrawals and deposits.
 */
@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/account")
public class BankController {
    
	@Autowired
	AccountService accountService;
	
	@Autowired
	IMessageService messageService;
	
	Logger logger = LoggerFactory.getLogger(BankController.class);
		
	/*
	 * Ping check
	 */
	@RequestMapping(value="/", produces = {"application/json"})
    public StatusResponse index() {
		logger.info("Hello");
        return new StatusResponse("Greetings from Spring Boot!");
    }
    
	/*
	 * Ping check
	 */
    @RequestMapping("/hello/{name}")
    String hello(@PathVariable String name) {
        return "Hello, " + name + "!";
    }
  
    /**
     * Account creation
     * @param Amount to be deposited
     * @return Account Id
     */
    @PostMapping(value="/create", produces = {"application/json"}, consumes = {"application/json"})
    TransactionResponse create(@RequestBody TransactionRequest req) { 
    	Long id = accountService.create(req.getAmt());
    	messageService.sendMessage(req.getIdAsString(),"create");
    	return new TransactionResponse(id, req.getAmt());
    }

    /**
     * Balance Check
     * @param Account Id
     * @return Balance in account
     */
    @PostMapping(value="/balance", produces = {"application/json"}, consumes = {"application/json"})
    TransactionResponse balanceCheck(@RequestBody TransactionRequest req) { 
    	messageService.sendMessage(req.getIdAsString(),"balance");
    	Double amt = accountService.getBalance(req.getId());
    	return new TransactionResponse(req.getId(), amt);
    }

    /**
     * Withdraw Amount
     * @param Account Id, Amount to be withdrawn
     * @return Success or error message
     */
    @PostMapping(value="/withdraw", produces = {"application/json"}, consumes = {"application/json"})
    StatusResponse withdrawAmount(@RequestBody TransactionRequest req) { 
    	messageService.sendMessage(req.getIdAsString(),"withdraw");
    	String status = accountService.withdraw(req.getId(), req.getAmt());
    	return new StatusResponse(status);
    }

    /**
     * Deposit Amount
     * @param Account Id, Amount to be deposited
     * @return Success or error message
     */
    @PostMapping(value="/deposit", produces = {"application/json"}, consumes = {"application/json"})
    StatusResponse depositCheck(@RequestBody TransactionRequest req) { 
    	messageService.sendMessage(req.getIdAsString(),"deposit");
    	String status = accountService.deposit(req.getId(), req.getAmt());
    	return new StatusResponse(status);
    }
    
    /**
     * Get details of 5 accounts  with highest inquiries/transactions for given operation
     * @param topic - balance/withdrawal
     * @return List of Accounts
     * @throws Exception
     */
    @GetMapping(value="/topTxn", produces = {"application/json"}, consumes = {"application/json"})
    List<TopAccountsResponse> getBalanceInqCount(@PathVariable String topic) throws Exception {
    	return messageService.getTopRecords(KafkaTopicEnum.compute(topic));
    }    
 }