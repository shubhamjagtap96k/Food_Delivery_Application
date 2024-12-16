package com.food.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.food.model.Customer;
import com.food.model.DeliveryDriver;
import com.food.model.OrderCoupon;
import com.food.model.OrderItem;
import com.food.model.Restaurant;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
	
	private Integer orderId;
    private LocalDateTime orderDate;
    private Integer customerId;
    private Integer restaurantId;
    private Integer deliveryDriverId;
    private String orderStatus;
    private List<OrderItemDTO> orderItems;
	
//    private List<OrderCoupon> orderCoupons;
}
