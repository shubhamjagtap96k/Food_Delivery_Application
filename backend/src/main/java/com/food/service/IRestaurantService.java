package com.food.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.food.dto.RestaurantDTO;
import com.food.model.MenuItem;
import com.food.model.Rating;
import com.food.model.Restaurant;


public interface IRestaurantService  {
	
	public List<RestaurantDTO> getAllRestaurants();
	
	public RestaurantDTO getByRestaurantId(int id);
	
	public String addRestaurant(RestaurantDTO restaurantdto);
	
	public String deleteRestaurant(int id);
	
	public String updateRestaurant(int id, RestaurantDTO restaurantdto);
	
	public List<MenuItem> getMenuItemsForRestaurant(int restaurantId);
	
	public List<Rating> getRatingForRestaurant(int restaurantId);
	
	public List<String> getDeliveryAreasForRestaurant(Integer restaurantId);
	
	public Restaurant findByUsernameAndPassword(String username, String password);
	
}

