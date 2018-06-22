package com.bank.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.bank.demo.dto.Message;
import com.bank.demo.services.AccountService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class BankController {
    
	@Autowired
	AccountService accountService;
	
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
    	Message msg = new Message(id.toString(),"balance");
    	sendMessage(id.toString(),msg);
    	return accountService.getBalance(id);
    }

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
     
    public void sendMessage(String key, Message msg) {
        kafkaTemplate.send("test", msg.getAcctId().toString()
        		, msg.getAcctId().toString()+":"+msg.getTransactionType());
    }
}