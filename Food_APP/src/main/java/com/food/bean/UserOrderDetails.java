package com.food.bean;

import java.util.Date;

public class UserOrderDetails {
	public String getRestrestaurantName() {
		return restrestaurantName;
	}
	public void setRestrestaurantName(String restrestaurantName) {
		this.restrestaurantName = restrestaurantName;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	public Date getOrder_date() {
		return order_date;
	}
	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}
	private int order_id;
	
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	private String restrestaurantName;
	private String item;
	private String price;
	private Date order_date;
	
}
