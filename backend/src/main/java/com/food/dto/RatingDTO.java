package com.food.dto;

import lombok.Data;

@Data
public class RatingDTO {
	
	    private Integer ratingId;
	    private Integer customerId;
	    private Integer orderId;
	    private Integer restaurantId;
	    private Integer rating;
	    private String review;
 
	}
