package com.food.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.food.bean.AddItem;
import com.food.bean.CartDetails;
import com.food.bean.CartResponse;
import com.food.bean.ItemDetails;
import com.food.bean.Login;
import com.food.bean.OrderDetails;
import com.food.bean.PlaceOrderDetails;
import com.food.bean.Registration;
import com.food.bean.RestaurantLogin;
import com.food.bean.RestaurantRegistration;
import com.food.bean.UserOrderDetails;
import com.food.exception.FoodException;
import com.food.response.FoodResponse;
import com.food.response.userDetails;

@Component
public interface FoodService {

	FoodResponse registerUser(Registration reg) throws FoodException;

	FoodResponse Login(Login user) throws FoodException;

	FoodResponse restaurantReg(RestaurantRegistration restaurant) throws FoodException;

	FoodResponse RestaurantLogin(RestaurantLogin login) throws FoodException;

	FoodResponse addItem(AddItem req) throws FoodException;

	List<AddItem> getItem(ItemDetails req) throws FoodException;

	FoodResponse saveOrder(OrderDetails req) throws FoodException;

	CartResponse getCart(CartDetails req) throws FoodException;

	FoodResponse placeOrder(PlaceOrderDetails req) throws FoodException;

	userDetails getUserDetails(userDetails mail) throws FoodException;

	AddItem getItemById(int id)  throws FoodException;

	userDetails getRestoDetails(userDetails mail) throws FoodException;

	List<UserOrderDetails> getUserOrderDetails(CartDetails mail) throws FoodException;

	List<UserOrderDetails> getrestoOrderDetails(CartDetails resto) throws FoodException;;



}
