package com.food.util;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class UUIDUtil {
	
	public static String randomUUID(){
		UUID uuid = UUID.randomUUID();
        return uuid.toString();
	}
}
