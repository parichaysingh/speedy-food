package com.food.bean;

public class OrderDetails {

	private String restaurantName;
	private String emailId;
	private String itemName;
	private String price;
	private String userAddress;
	private String noOFPlates;
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	
	public String getRestaurantName() {
		return restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public String getNoOFPlates() {
		return noOFPlates;
	}
	public void setNoOFPlates(String noOFPlates) {
		this.noOFPlates = noOFPlates;
	}
	
}
