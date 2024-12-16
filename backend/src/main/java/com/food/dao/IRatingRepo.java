package com.food.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.model.Rating;

@Repository
public interface IRatingRepo extends JpaRepository<Rating, Integer> {
	List<Rating> findByRestaurantRestaurantId(Integer restaurantId);
	
	public List<Rating> findByCustomersCustomerId (Integer customerId);
}
