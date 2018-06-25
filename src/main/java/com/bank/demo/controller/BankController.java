package com.bank.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.bank.demo.dto.BalanceInq;
import com.bank.demo.dto.TopicEnum;
import com.bank.demo.dto.TxnReq;
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
    
    @GetMapping("/balance/{id}")
    String balanceCheck(@PathVariable Long id) { 
    	kafkaService.sendMessage(id.toString(),"balance");
    	return accountService.getBalance(id);
    }

    @PostMapping("/withdraw/{id}")
    String withdrawCheck(@PathVariable Long id, @RequestBody TxnReq req) { 
    	kafkaService.sendMessage(id.toString(),"withdraw");
    	return accountService.withdraw(id, req.getAmt());
    }

    @PostMapping("/deposit/{id}")
    String depositCheck(@PathVariable Long id, @RequestBody TxnReq req) { 
    	kafkaService.sendMessage(id.toString(),"deposit");
    	return accountService.deposit(id, req.getAmt());
    }
    
    @GetMapping("/topTxn/{topic}")
    ArrayList<BalanceInq> getBalanceInqCount(@PathVariable String topic) throws Exception {
    	return kafkaService.getRecords(TopicEnum.compute(topic));
    }    
 }