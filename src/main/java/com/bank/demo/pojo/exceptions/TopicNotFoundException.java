package com.bank.demo.pojo.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Topic Not Found")  // 404
public class TopicNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7390692459689086434L;
}