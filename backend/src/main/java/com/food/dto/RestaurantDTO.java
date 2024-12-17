package com.food.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {
	
	private Integer restaurantId;
    private String restaurantName;
    private String restaurantAddress;
    private String restaurantPhone;
    private List<String> deliveryAreas;
    private String password;
    private String image;

//    private List<MenuItemDTO> menuItems;
//    private List<OrderDTO> orders;
//    private List<RatingDTO> ratings;
}
