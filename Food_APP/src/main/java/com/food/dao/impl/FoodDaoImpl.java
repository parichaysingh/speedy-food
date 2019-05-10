package com.food.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.food.bean.AddItem;
import com.food.bean.CartDetails;
import com.food.bean.ItemDetails;
import com.food.bean.Login;
import com.food.bean.OrderDetails;
import com.food.bean.Orders;
import com.food.bean.PlaceOrderDetails;
import com.food.bean.Registration;
import com.food.bean.RestaurantLogin;
import com.food.bean.RestaurantRegistration;
import com.food.constant.FoodQuery;
import com.food.dao.FoodDao;
import com.food.exception.FoodException;
import com.food.response.userDetails;

@Component
public class FoodDaoImpl implements FoodDao {

	@Resource
	@Qualifier(value="clJdbcTemplate")
	JdbcTemplate jdbcTemplate;
	
	@Resource
	@Qualifier(value="clNamedParameterJdbcTemplate")
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	
	
	
	@Override
	public void register(Registration reg) throws FoodException {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("email",reg.getEmail());
		params.addValue("name", reg.getName());
		params.addValue("password", reg.getPassword());
		namedParameterJdbcTemplate.update(FoodQuery.SAVE_DETAILS, params);
	}




	@Override
	public List<Map<String, Object>> findMyUid(Login user) throws FoodException {
		try
		{
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("email", user.getEmail());	
		return namedParameterJdbcTemplate.queryForList(FoodQuery.GET_USER_INFO, params) ;
		}catch (Exception e){
			throw new FoodException(e.getMessage(),e);
		}
	}




	@Override
	public void restaurantRegister(RestaurantRegistration restaurant) throws FoodException {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("email",restaurant.getEmail());
		params.addValue("RestaurantName", restaurant.getRestaurantName());
		params.addValue("password", restaurant.getPassword());
		namedParameterJdbcTemplate.update(FoodQuery.SAVE_RESTAURANT_DETAILS, params);
		
	}




	@Override
	public List<Map<String, Object>> findMyRestoUid(RestaurantLogin login) throws FoodException {
		try
		{
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("email", login.getEmail());	
		return namedParameterJdbcTemplate.queryForList(FoodQuery.GET_RESTO_INFO, params) ;
		}catch (Exception e){
			throw new FoodException(e.getMessage(),e);
		}
	}




	@Override
	public int addItem(AddItem req) throws FoodException {
		try
		{
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("resName",req.getRestaurantName().toUpperCase());
		params.addValue("city",req.getCity().toUpperCase());
		params.addValue("place",req.getPlace().toUpperCase());
		params.addValue("itName1",req.getItemName1());
		params.addValue("price1",req.getPrice1());
		params.addValue("itName2",req.getItemName2());
		params.addValue("itName3",req.getItemName3());
		params.addValue("price2",req.getPrice2());
		params.addValue("price3",req.getPrice3());
		
		return namedParameterJdbcTemplate.update(FoodQuery.SAVE_MENU_DETAILS, params);
		}
		catch (Exception e){
			throw new FoodException(e.getMessage(),e);
		}
	}




	@Override
	public List<Map<String, Object>> getItem(ItemDetails req) throws FoodException {
		try
		{
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("city", req.getCity());	
		params.addValue("place", req.getPlace());
		return namedParameterJdbcTemplate.queryForList(FoodQuery.GET_ITEM_DETAILS, params) ;
		}catch (Exception e){
			throw new FoodException(e.getMessage(),e);
		}
		
	}




	@Override
	public int saveOrder(OrderDetails req) throws FoodException {
		
		try
		{
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		params.addValue("emailId",req.getEmailId());
		params.addValue("itemName",req.getItemName());
		params.addValue("userAddress",req.getUserAddress());
		params.addValue("noofplates",req.getNoOFPlates());
		params.addValue("price",req.getPrice());
		params.addValue("restaurantName", req.getRestaurantName());
		return namedParameterJdbcTemplate.update(FoodQuery.SAVE_ORDER, params);
		}
		catch (Exception e){
			throw new FoodException(e.getMessage(),e);
		}
	}




	@Override
	public List<Map<String, Object>> getCart(CartDetails req) throws FoodException {
		try
		{
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("emailId", req.getEmailId());	
		return namedParameterJdbcTemplate.queryForList(FoodQuery.GET_CART_DETAILS, params) ;
		}catch (Exception e){
			throw new FoodException(e.getMessage(),e);
		}
	}




	@Override
	public int placeOrder(PlaceOrderDetails req) throws FoodException {
	
		
			MapSqlParameterSource params = new MapSqlParameterSource();	
			params.addValue("emailId",req.getEmailId());
			params.addValue("phoneNumber", req.getPhoneNumber());
			params.addValue("address",req.getAddress());
			params.addValue("orderedDate",new Date());
			 namedParameterJdbcTemplate.update(FoodQuery.SAVE_ORDER_DETAILS, params);
			
		
		
		return 0;
		
	}




	@Override
	public Map<String, Object> getUserDetails(userDetails mail) throws FoodException {
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("email", mail.getEmail());
			return namedParameterJdbcTemplate.queryForMap(FoodQuery.GET_USER_INFO, params) ;
			}catch (Exception e){
				throw new FoodException(e.getMessage(),e);
			}
	}




	@Override
	public Map<String, Object> getItemById(int id) throws FoodException {
		try
		{
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id",id);	
	
		return namedParameterJdbcTemplate.queryForMap(FoodQuery.GET_ITEM_DETAILS_BY_ID, params) ;
		}catch (Exception e){
			throw new FoodException(e.getMessage(),e);
		}
		
	}




	@Override
	public Map<String, Object> getRestoDetails(userDetails mail) throws FoodException {
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("email", mail.getEmail());
			return namedParameterJdbcTemplate.queryForMap(FoodQuery.GET_RESTO_INFO, params) ;
			}catch (Exception e){
				throw new FoodException(e.getMessage(),e);
			}
	}




	@Override
	public List<Map<String, Object>> getUserOrderDetails(CartDetails mail) throws FoodException {
		try
		{
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("emailId", mail.getEmailId());	
		return namedParameterJdbcTemplate.queryForList(FoodQuery.GET_USER_ORDER_DETAILS, params) ;
		}catch (Exception e){
			throw new FoodException(e.getMessage(),e);
		}
	}




	@Override
	public void updateOrder(PlaceOrderDetails req) throws FoodException {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("emailId",req.getEmailId());	
		namedParameterJdbcTemplate.update(FoodQuery.UPDATE_ORDER_DETAILS, params);
	}




	@Override
	public List<Map<String, Object>> getRestOrderDetails(CartDetails resto) throws FoodException {
		try
		{
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name",resto.getResto());	
		return namedParameterJdbcTemplate.queryForList(FoodQuery.GET_RESTO_ORDER_DETAILS, params) ;
		}catch (Exception e){
			throw new FoodException(e.getMessage(),e);
		}
	}
	}


