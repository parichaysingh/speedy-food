package com.food.exception;

import org.springframework.stereotype.Component;

@Component
public class FoodException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public FoodException() {}
	
	public FoodException(String errorMessage){
		super(errorMessage);
	}
	
	public FoodException(String errorMessage,Exception e){
		super(errorMessage,e);
	}

}
