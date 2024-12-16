package com.food.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderItemDTO {
    private Integer orderItemId;
    private Integer quantity;
    private BigDecimal price; // Use BigDecimal for accurate price representation
    private String itemName;  
}
