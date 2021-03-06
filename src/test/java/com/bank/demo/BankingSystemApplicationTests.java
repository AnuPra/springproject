package com.bank.demo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.bank.demo.pojo.request.TransactionRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BankingSystemApplicationTests {

   	@Test
	public void contextLoads() {
	}
	
	@Autowired
    private MockMvc mvc;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	public void createAccount() throws Exception {
		
		TransactionRequest transactionRequest = new TransactionRequest();
		transactionRequest.setAmt(9876.00);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/account/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(transactionRequest));
		
		mvc.perform(requestBuilder)
        .andExpect(status().isOk())
        .andExpect(content().string(equalTo("{\"id\":3,\"amt\":9876.0}")));
	}
	
	@Ignore
    @Test
    public void getHello() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/account"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Greetings from Spring Boot!")));
    }

    @Test
    public void balanceReturns200() throws Exception {
    	RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/account/balance")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new TransactionRequest(1)));
		
    	mvc.perform(requestBuilder)
    	.andExpect(status().isOk())
    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
    	.andExpect(jsonPath("$.amt", is(5000.0)));
    }
    
    @Test
    public void depositReturns200() throws Exception {
    	
    	RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/account/deposit")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new TransactionRequest(1,98.00)));
		
    	mvc.perform(requestBuilder)
    	.andExpect(status().isOk())
    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
    	.andExpect(jsonPath("$.status", is("Successful Deposit")));
    }

    @Test
    public void depositThrows404() throws Exception {
    	
    	RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/account/deposit")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new TransactionRequest(3,98.00)));
		
    	mvc.perform(requestBuilder)
    	.andExpect(status().isNotFound());
    }

    @Test
    public void withdrawalReturns200() throws Exception {
    	
    	RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/account/withdraw")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new TransactionRequest(1,98.00)));
	
    	mvc.perform(requestBuilder)
    	.andExpect(status().isOk())
    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
    	.andExpect(jsonPath("$.status", is("Successful withdrawal")));
    }
    
    @Test
    public void withdrawalThrows404() throws Exception {
    	
    	RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/account/withdraw")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new TransactionRequest(3,98.00)));
	
    	mvc.perform(requestBuilder)
    	.andExpect(status().isNotFound());
    }
 }
