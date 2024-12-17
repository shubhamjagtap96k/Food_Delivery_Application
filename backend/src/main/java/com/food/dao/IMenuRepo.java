package com.food.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.model.MenuItem;

@Repository
public interface IMenuRepo extends JpaRepository<MenuItem, Integer> {
	 List<MenuItem> findByRestaurantRestaurantId(Integer restaurantId);
}
