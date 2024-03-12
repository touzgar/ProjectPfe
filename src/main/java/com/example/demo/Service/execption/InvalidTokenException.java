package com.example.demo.Service.execption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class InvalidTokenException extends RuntimeException {
	
	private String massage;
	public InvalidTokenException(String message) {
		super(message);
	}

}
