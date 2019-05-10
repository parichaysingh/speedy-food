package com.food.bean;

import java.util.List;

public class CartResponse {

	private List<Orders> itemNames;
	public List<Orders> getItemNames() {
		return itemNames;
	}
	public void setItemNames(List<Orders> itemNames) {
		this.itemNames = itemNames;
	}
	
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	private int totalPrice;
}
