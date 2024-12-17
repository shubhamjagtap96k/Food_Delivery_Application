package com.food.service;

import java.util.List;

import com.food.model.Rating;

public interface IRatingService {

	List<Rating> getReviewsByRestaurantId(int restaurantId);
}
