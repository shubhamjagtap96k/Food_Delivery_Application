package com.food.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.model.Restaurant;

@Repository
public interface IRestaurantRepo extends JpaRepository<Restaurant, Integer> {
	
	Optional<Restaurant> findByRestaurantName(String restaurantName);
	
	Restaurant findByRestaurantNameAndPassword(String restaurantName, String password);
}
