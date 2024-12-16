package com.food.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.dao.IRatingRepo;
import com.food.model.Rating;

@Service
public class RatingService implements IRatingService {
	
	@Autowired
	IRatingRepo ratingRepo;
	
	public List<Rating> getReviewsByRestaurantId(int restaurantId) {
        return ratingRepo.findByRestaurantRestaurantId(restaurantId);
    }
}
