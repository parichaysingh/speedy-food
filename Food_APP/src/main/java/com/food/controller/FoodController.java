package com.food.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.food.bean.AddItem;
import com.food.bean.CartDetails;
import com.food.bean.CartResponse;
import com.food.bean.GetCartDetails;
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
import com.food.service.FoodService;

@RestController
@RequestMapping(value = "/food/api/v1", produces = "application/json")
public class FoodController {
	private static final Logger LOG = LoggerFactory.getLogger(FoodController.class);
	
	@Autowired
	FoodService foodService;
	
	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	private FoodResponse registerUser(@RequestBody Registration reg) throws FoodException{
		return foodService.registerUser(reg);
	}
	
	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	private FoodResponse login(@RequestBody Login user) throws FoodException {
		return foodService.Login(user);
	}
	@RequestMapping(value = "/RestaurantRegister", method = RequestMethod.POST)
	private FoodResponse restaurantReg(@RequestBody RestaurantRegistration restaurant) throws FoodException{
		return foodService.restaurantReg(restaurant);
	}
	
	@RequestMapping(value = "/RestaurantLogin", method = RequestMethod.POST)
	private FoodResponse RestaurantLogin(@RequestBody RestaurantLogin login) throws FoodException {
		return foodService.RestaurantLogin(login);
	}
	
	@RequestMapping(value = "/addItemDetails", method = RequestMethod.POST)
	private FoodResponse addItem(@RequestBody AddItem req) throws FoodException {
		return foodService.addItem(req);
	}
	@RequestMapping(value = "/getItemDetails", method = RequestMethod.POST)
	private List<AddItem> getItem(@RequestBody ItemDetails req) throws FoodException {
		return foodService.getItem(req);
	}
	@RequestMapping(value = "/getItemDetails/{id}", method = RequestMethod.GET)
	private AddItem getItem(@PathVariable int id) throws FoodException {
		return foodService.getItemById(id);
	}
	/*@RequestMapping(value = "/getItemDetails", method = RequestMethod.POST)
	private List<GetCartDetails> getItem(@RequestBody  cart) throws FoodException {
		return foodService.getItem(cart);
	}*/
	@RequestMapping(value = "/addToCart", method = RequestMethod.POST)
	private FoodResponse saveOrder(@RequestBody OrderDetails req) throws FoodException {
		return foodService.saveOrder(req);
	}
	
	@RequestMapping(value = "/getCartDetails", method = RequestMethod.POST)
	private CartResponse getCart(@RequestBody CartDetails req) throws FoodException {
		return foodService.getCart(req);
	}
	
	@RequestMapping(value = "/placeOrder", method = RequestMethod.POST)
	private FoodResponse placeOrder(@RequestBody PlaceOrderDetails req) throws FoodException {
		return foodService.placeOrder(req);
	}
	
	@RequestMapping(value = "/getUserdetails", method = RequestMethod.POST)
	private userDetails getUserDetails(@RequestBody userDetails mail) throws FoodException {
		return foodService.getUserDetails(mail);
	}
	
	@RequestMapping(value = "/getRestodetails", method = RequestMethod.POST)
	private userDetails getRestoDetails(@RequestBody userDetails mail) throws FoodException {
		return foodService.getRestoDetails(mail);
	}
	
	@RequestMapping(value = "/getUserOrderdetails", method = RequestMethod.POST)
	private List<UserOrderDetails> getUserOrserDetails(@RequestBody CartDetails mail) throws FoodException {
		return foodService.getUserOrderDetails(mail);
	}
	@RequestMapping(value = "/getRestOrderdetails", method = RequestMethod.POST)
	private List<UserOrderDetails> getRestrOrserDetails(@RequestBody CartDetails resto) throws FoodException {
		return foodService.getrestoOrderDetails(resto);
	}
}
