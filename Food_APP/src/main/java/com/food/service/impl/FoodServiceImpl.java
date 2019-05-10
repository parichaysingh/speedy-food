package com.food.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.food.bean.AddItem;
import com.food.bean.CartDetails;
import com.food.bean.CartResponse;
import com.food.bean.ItemDetails;
import com.food.bean.Login;
import com.food.bean.OrderDetails;
import com.food.bean.Orders;
import com.food.bean.PlaceOrderDetails;
import com.food.bean.Registration;
import com.food.bean.RestaurantRegistration;
import com.food.bean.UserOrderDetails;
import com.food.dao.FoodDao;
import com.food.exception.FoodException;
import com.food.response.FoodResponse;
import com.food.response.userDetails;
import com.food.service.FoodService;
import com.food.util.PasswordHashingUtility;
import com.sun.mail.iap.Response;

@Component
public class FoodServiceImpl implements FoodService {
	
	@Autowired
	FoodDao foodDao;
	
	@Autowired
	PasswordHashingUtility passwordHashingUtility;
	
	@Override
	public FoodResponse registerUser(Registration reg) throws FoodException {
		FoodResponse response=new FoodResponse();
		reg.setPassword(passwordHashingUtility.getPasswordHash(reg.getPassword()));
		foodDao.register(reg);
		response.setMessage("User SucessFully Registred");
		response.setCode(HttpStatus.OK);
		
		return response;
		
	}

	@Override
	public FoodResponse Login(Login user) throws FoodException {
		FoodResponse response =new FoodResponse();
		List<Map<String,Object>> userDetails=foodDao.findMyUid(user);
		Map<String, Object> userIdMap=userDetails.get(0);
		String hashpassword=(String) userIdMap.get("password");
	String password=user.getPassword();
		if(!userDetails.isEmpty())
		{
			if(user.getPassword().equals(password))
			{
				response.setMessage("sucessfully loged in");
				response.setCode(HttpStatus.OK);	
			}
			else
			{
				response.setMessage("incorrect password / mail");
				response.setCode(HttpStatus.OK);
			}
			
			
		}
		else
		{
			response.setMessage("user already registerd");
			response.setCode(HttpStatus.OK);
		}
		return response;
	}

	@Override
	public FoodResponse restaurantReg(RestaurantRegistration restaurant) throws FoodException {
		FoodResponse response=new FoodResponse();
		foodDao.restaurantRegister(restaurant);
		response.setMessage("restaurant SucessFully Registred");
		response.setCode(HttpStatus.OK);
		
		return response;
	}

	@Override
	public FoodResponse RestaurantLogin(com.food.bean.RestaurantLogin login) throws FoodException {
		FoodResponse response =new FoodResponse();
		List<Map<String,Object>> userDetails=foodDao.findMyRestoUid(login);
		Map<String, Object> userIdMap=userDetails.get(0);
		String hashpassword=(String) userIdMap.get("password");
		String password=login.getPassword();
		if(!userDetails.isEmpty())
		{
			boolean passwordVerified = verifyUserPassword(password,hashpassword);
			if(passwordVerified)
			{
				response.setMessage("sucessfully loged in");
				response.setCode(HttpStatus.OK);	
			}
			else
			{
				response.setMessage("incorrect password / mail");
				response.setCode(HttpStatus.OK);
			}
			
			
		}
		else
		{
			response.setMessage("Restaurant already registerd");
			response.setCode(HttpStatus.OK);
		}
		return response;
	}

	@Override
	public FoodResponse addItem(AddItem req) throws FoodException {
		FoodResponse response =new FoodResponse();
		int result=foodDao.addItem(req);
		response.setMessage("successfully stored");
		response.setCode(HttpStatus.OK);
		return response;
		
	}

	@Override
	public List<AddItem> getItem(ItemDetails req) throws FoodException {
		List<AddItem> response=new ArrayList<AddItem>();
		List<Map<String, Object>> itemDetailsList=foodDao.getItem(req);
		if(!itemDetailsList.isEmpty())
		{
			for(int i=0;i<itemDetailsList.size();i++)
			{
				Map<String, Object> itemDetailsMap=itemDetailsList.get(i);
				AddItem item=new AddItem();
				item.setId((int) itemDetailsMap.get("id"));
				item.setRestaurantName((String) itemDetailsMap.get("restaurant_name"));
				item.setCity((String) itemDetailsMap.get("city"));
				item.setPlace((String) itemDetailsMap.get("place"));
				item.setItemName1((String) itemDetailsMap.get("item_1"));
				item.setPrice1((String) itemDetailsMap.get("price_1"));
				item.setItemName2((String) itemDetailsMap.get("item_2"));
				item.setPrice2((String) itemDetailsMap.get("price_2"));
				item.setItemName3((String) itemDetailsMap.get("item_3"));
				item.setPrice3((String) itemDetailsMap.get("price_3"));
				response.add(item);
			}
		}
		return response;
	}

	@Override
	public FoodResponse saveOrder(OrderDetails req) throws FoodException {
		
		FoodResponse response =new FoodResponse();
		int result=foodDao.saveOrder(req);
		response.setMessage("Add to cart successfully");
		response.setCode(HttpStatus.OK);
		return response;
	}

	@Override
	public CartResponse getCart(CartDetails req) throws FoodException {
		CartResponse response=new CartResponse();
		int totalPriceValue=0;
		List<Orders> orders=new ArrayList<Orders>();
		List<Map<String, Object>> itemDetailsList=foodDao.getCart(req);
		if(!itemDetailsList.isEmpty())
		{
			for(int i=0;i<itemDetailsList.size();i++)
			{
				Map<String, Object> itemMap=itemDetailsList.get(i);
				Orders order=new Orders();
				order.setRestName((String) itemMap.get("rest_name"));
				order.setItemName((String) itemMap.get("item"));
				String price=(String) itemMap.get("price");
				int priceValue=Integer.parseInt(price);
				order.setEmailId((String) itemMap.get("email"));
				String count=(String) itemMap.get("no_of_plates");
				order.setAddress((String) itemMap.get("address"));
				order.setNoOFPlates((String) itemMap.get("no_of_plates"));
				order.setOrderId( (int) itemMap.get("order_id"));

				int totalPriceCount=Integer.parseInt(count);
				int total=priceValue*totalPriceCount;
				String total1=String.valueOf(total);
				order.setPrice(price);
				order.setTotalPrice(total1);
				totalPriceValue=totalPriceValue+total;
				
				orders.add(order);
			}
		}
		response.setItemNames(orders);
		response.setTotalPrice(totalPriceValue);
		return response;
	}

	@Override
	public FoodResponse placeOrder(PlaceOrderDetails req) throws FoodException {
		FoodResponse response=new FoodResponse();
		int result=foodDao.placeOrder(req);
		foodDao.updateOrder(req);
		if(result==0)
		{
			response.setMessage("order placed successfully");
			response.setCode(HttpStatus.OK);
		}
		return response;
	}

	@Override
	public userDetails getUserDetails(userDetails mail) throws FoodException {
		userDetails ud=new userDetails();
		Map<String, Object> userDetailsMap=foodDao.getUserDetails(mail);
		String email=(String) userDetailsMap.get("email");
		String name=(String) userDetailsMap.get("name");
		ud.setEmail(email);
		ud.setName(name);
		return ud;

	}

	@Override
	public AddItem getItemById(int id) throws FoodException {
		AddItem item=new AddItem();
		Map<String, Object> itemDetailsList=foodDao.getItemById(id);
		if(!itemDetailsList.isEmpty())
		{
			
				item.setId((int) itemDetailsList.get("id"));
				item.setRestaurantName((String) itemDetailsList.get("restaurant_name"));
				item.setCity((String) itemDetailsList.get("city"));
				item.setPlace((String) itemDetailsList.get("place"));
				item.setItemName1((String) itemDetailsList.get("item_1"));
				item.setPrice1((String) itemDetailsList.get("price_1"));
				item.setItemName2((String) itemDetailsList.get("item_2"));
				item.setPrice2((String) itemDetailsList.get("price_2"));
				item.setItemName3((String) itemDetailsList.get("item_3"));
				item.setPrice3((String) itemDetailsList.get("price_3"));
				
			}
		
		return item;
	}

	@Override
	public userDetails getRestoDetails(userDetails mail) throws FoodException {
		userDetails ud=new userDetails();
		Map<String, Object> userDetailsMap=foodDao.getRestoDetails(mail);
		String email=(String) userDetailsMap.get("email");
		String name=(String) userDetailsMap.get("RestaurantName");
		ud.setEmail(email);
		ud.setName(name);
		return ud;
	}

	@Override
	public List<UserOrderDetails> getUserOrderDetails(CartDetails mail) throws FoodException {
		 List<UserOrderDetails> uod=new ArrayList<>();
		 List<Map<String, Object>> userOrderDetailsList=foodDao.getUserOrderDetails(mail);
		 if(!userOrderDetailsList.isEmpty())
			{
				for(int i=0;i<userOrderDetailsList.size();i++)
				{
			
				
					Map<String, Object> itemMap=userOrderDetailsList.get(i);
					UserOrderDetails order =new UserOrderDetails();
					order.setOrder_id((int) itemMap.get("order_id"));
					order.setItem((String) itemMap.get("item"));
					order.setPrice((String) itemMap.get("price"));
					order.setRestrestaurantName((String) itemMap.get("rest_name"));
					order.setOrder_date( (Date) itemMap.get("ordered_date"));
					uod.add(order);
					
				
		
	}
			}
		return uod;}
	
	private boolean verifyUserPassword(String password,String hashedPassword) throws FoodException {
		if(passwordHashingUtility.verifyPassword(password, hashedPassword)){
			return true;
		}
		return false;
	}

	@Override
	public List<UserOrderDetails> getrestoOrderDetails(CartDetails resto) throws FoodException {
		 List<UserOrderDetails> uod=new ArrayList<>();
		 List<Map<String, Object>> userOrderDetailsList=foodDao.getRestOrderDetails(resto);
		 if(!userOrderDetailsList.isEmpty())
			{
				for(int i=0;i<userOrderDetailsList.size();i++)
				{
			
				
					Map<String, Object> itemMap=userOrderDetailsList.get(i);
					UserOrderDetails order =new UserOrderDetails();
					order.setItem((String) itemMap.get("item"));
					order.setPrice((String) itemMap.get("price"));
					order.setOrder_id((int) itemMap.get("order_id"));
					order.setRestrestaurantName((String) itemMap.get("phoneno"));
					order.setRestrestaurantName((String) itemMap.get("address"));
					order.setRestrestaurantName((String) itemMap.get("email"));
					order.setOrder_date( (Date) itemMap.get("ordered_date"));
					uod.add(order);
					
				
		
	}
			}
		return uod;}
	
	}


	


