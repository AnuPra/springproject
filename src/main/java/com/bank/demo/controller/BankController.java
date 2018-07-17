package com.bank.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.bank.demo.pojo.enums.KafkaTopicEnum;
import com.bank.demo.pojo.request.TransactionRequest;
import com.bank.demo.pojo.response.StatusResponse;
import com.bank.demo.pojo.response.TopAccountsResponse;
import com.bank.demo.pojo.response.TransactionResponse;
import com.bank.demo.services.AccountService;
import com.bank.demo.services.KafkaService;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController("account")
public class BankController {
    
	@Autowired
	AccountService accountService;
	
	@Autowired
	KafkaService kafkaService;
	
	@RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }
    
    @RequestMapping("/hello/{name}")
    String hello(@PathVariable String name) {
        return "Hello, " + name + "!";
    }
  
    @PostMapping(value="/create", produces = {"application/json"}, consumes = {"application/json"})
    TransactionResponse create(@RequestBody TransactionRequest req) { 
    	Long id = accountService.create(req.getAmt());
    	kafkaService.sendMessage(id.toString(),"create");
    	return new TransactionResponse(id, req.getAmt());
    }

    @GetMapping(value="/balance/{id}", produces = {"application/json"}, consumes = {"application/json"})
    TransactionResponse balanceCheck(@PathVariable Long id) { 
    	kafkaService.sendMessage(id.toString(),"balance");
    	Double amt = accountService.getBalance(id);
    	return new TransactionResponse(id, amt);
    }

    @PostMapping(value="/withdraw/{id}", produces = {"application/json"}, consumes = {"application/json"})
    StatusResponse withdrawCheck(@PathVariable Long id, @RequestBody TransactionRequest req) { 
    	kafkaService.sendMessage(id.toString(),"withdraw");
    	String status = accountService.withdraw(id, req.getAmt());
    	return new StatusResponse(status);
    }

    @PostMapping(value="/deposit/{id}", produces = {"application/json"}, consumes = {"application/json"})
    StatusResponse depositCheck(@PathVariable Long id, @RequestBody TransactionRequest req) { 
    	kafkaService.sendMessage(id.toString(),"deposit");
    	String status = accountService.deposit(id, req.getAmt());
    	return new StatusResponse(status);
    }
    
    @GetMapping(value="/topTxn/{topic}", produces = {"application/json"}, consumes = {"application/json"})
    ArrayList<TopAccountsResponse> getBalanceInqCount(@PathVariable String topic) throws Exception {
    	return kafkaService.getTopRecords(KafkaTopicEnum.compute(topic));
    }    
 }