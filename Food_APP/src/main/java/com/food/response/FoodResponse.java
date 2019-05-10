package com.food.response;

import org.springframework.http.HttpStatus;

public class FoodResponse {
	String message;
	HttpStatus code;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public HttpStatus getCode() {
		return code;
	}
	public void setCode(HttpStatus code) {
		this.code = code;
	}

}
