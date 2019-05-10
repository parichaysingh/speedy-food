package com.food.constant;

public interface FoodQuery {

	String SAVE_DETAILS ="insert into user_reg(name,email,password) values(:name,:email,:password)";
	String GET_USER_INFO ="select * from user_reg where email=:email";
	String SAVE_RESTAURANT_DETAILS = "insert into Restaurant_Reg(RestaurantName,email,password) values(:RestaurantName,:email,:password)";
	String GET_RESTO_INFO = "select * from Restaurant_Reg where email=:email";
	String SAVE_MENU_DETAILS = "insert into menu_table(restaurant_name,city,place,item_1,price_1,item_2,price_2,item_3,price_3) values(:resName,:city,:place,:itName1,:price1,:itName2,:price2,:itName3,:price3)";
	String GET_ITEM_DETAILS = "select * from menu_table where city=:city and place=:place";
	String SAVE_ORDER = "insert into order_details(item,price,email,address,no_of_plates,rest_name) values(:itemName,:price,:emailId,:userAddress,:noofplates,:restaurantName)";
	String GET_CART_DETAILS = "select * from order_details where email=:emailId and order_status='no'";
	String SAVE_ORDER_DETAILS = "insert into placed_orders(email,phoneno,address,ordered_date) values(:emailId,:phoneNumber,:address,:orderedDate)";
	String GET_ITEM_DETAILS_BY_ID = "select * from menu_table where id=:id ";
	String GET_USER_ORDER_DETAILS = "SELECT  rest_name,item,price,ordered_date,order_id FROM food.placed_orders p,order_details o where p.email=:emailId = o.email=:emailId ;";
	String UPDATE_ORDER_DETAILS ="update order_details set order_status='yes' where email=:emailId";
	String GET_RESTO_ORDER_DETAILS ="SELECT  email,item,price,order_id FROM order_details where rest_name=:name";

}
