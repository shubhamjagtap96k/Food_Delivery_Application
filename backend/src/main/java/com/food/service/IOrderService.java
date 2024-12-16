package com.food.service;

import java.util.List;

import com.food.dto.OrderDTO;
import com.food.model.Order;


public interface IOrderService {
	public String saveOrder(OrderDTO orderDTO);

	public OrderDTO getByOrderId(int id);

	public String deleteOrder(int id);

	public String updateOrderStatus(int id, OrderDTO orderDTO);
	
	List<OrderDTO> getOrdersByRestaurantId(int restaurantId);
}
