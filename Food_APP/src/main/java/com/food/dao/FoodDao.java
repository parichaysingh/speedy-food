package com.food.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.food.bean.AddItem;
import com.food.bean.CartDetails;
import com.food.bean.ItemDetails;
import com.food.bean.Login;
import com.food.bean.OrderDetails;
import com.food.bean.PlaceOrderDetails;
import com.food.bean.Registration;
import com.food.bean.RestaurantLogin;
import com.food.bean.RestaurantRegistration;
import com.food.exception.FoodException;
import com.food.response.userDetails;

@Component
public interface FoodDao {

	void register(Registration reg) throws FoodException;

	List<Map<String, Object>> findMyUid(Login user)throws FoodException;

	void restaurantRegister(RestaurantRegistration restaurant)throws FoodException;

	List<Map<String, Object>> findMyRestoUid(RestaurantLogin login)throws FoodException;

	int addItem(AddItem req) throws FoodException;

	List<Map<String, Object>> getItem(ItemDetails req) throws FoodException;

	int saveOrder(OrderDetails req)  throws FoodException;

	List<Map<String, Object>> getCart(CartDetails req) throws FoodException;

	int placeOrder(PlaceOrderDetails req) throws FoodException;

	Map<String, Object> getUserDetails(userDetails mail) throws FoodException;

	Map<String, Object> getItemById(int id) throws FoodException;

	Map<String, Object> getRestoDetails(userDetails mail) throws FoodException;

	List<Map<String, Object>> getUserOrderDetails(CartDetails mail) throws FoodException;

	void updateOrder(PlaceOrderDetails req) throws FoodException;

	List<Map<String, Object>> getRestOrderDetails(CartDetails resto) throws FoodException;
;


}
