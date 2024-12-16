package com.food.dto;

import java.util.List;
import java.util.Set;

import com.food.model.DeliveryAddresses;

import lombok.Data;
 
@Data
public class CustomerDTO {
	private Integer customerId;
	private String customerName;
	private String customerEmail;
	private String customerPhone;
	private String password;
	private List<DeliveryAddresses> deliveryAddresses;
 
	private List<OrderDTO> orders;
	 private Set<RestaurantDTO> favoriteRestaurants;
	List<RatingDTO> rating;
 
}
                             