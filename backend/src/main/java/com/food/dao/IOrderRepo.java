package com.food.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food.model.Order;
import com.food.model.Restaurant;

public interface IOrderRepo extends JpaRepository<Order, Integer> {
	
	List<Order> findByDeliveryDriverDriverId(Integer driverId);
	
	List<Order> findByCustomersCustomerId (Integer customerId);
	
	List<Order> findByRestaurantRestaurantId (Integer restaurantId);
}
