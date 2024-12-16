package com.food.dto;

import java.math.BigDecimal; // Use BigDecimal for itemPrice

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemDTO {
    
    private Integer itemId;
    
    private String itemName;
    
    private String itemDescription;
    
    private BigDecimal itemPrice; 
    
    private int restaurantId;
    
    private String image;
}
