package com.food.service;

import java.util.List;

import com.food.dto.CustomerDTO;
import com.food.dto.OrderDTO;
import com.food.dto.RatingDTO;
import com.food.dto.RestaurantDTO;


 
 
public interface ICustomerService {
 
//	public CustomerDTO saveCustomer(CustomerDTO customerDTO);
 
	public List<CustomerDTO> getAllCustomers();
 
	public CustomerDTO getCustomerById(Integer customerId);
 
	public CustomerDTO updateCustomer(CustomerDTO customerDTO);
 
	public void deleteCustomer(Integer customerId);
 
	public CustomerDTO saveCustomer(CustomerDTO customerDTO);
 
	public List<OrderDTO> getOrdersByCustomerId(Integer customerId);
 
	public CustomerDTO addFavoriteRestaurant(Integer customerId, RestaurantDTO restaurantDTO);
	public void removeFavoriteRestaurant(Integer customerId, Integer restaurantId);
 
	List<RatingDTO> getCustomerReviews(Integer customerId);
	
	public CustomerDTO authenticateCustomer(String username, String password);

 
 
 
}
