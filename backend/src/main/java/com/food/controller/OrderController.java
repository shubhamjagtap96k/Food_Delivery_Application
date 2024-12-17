package com.food.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.dto.OrderDTO;
import com.food.exception.ResourceNotFoundException;
import com.food.model.Order;
import com.food.service.IOrderService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	IOrderService orderService;

	@PostMapping
	public ResponseEntity<String> saveOrder(@Valid @RequestBody OrderDTO orderDTO) throws ResourceNotFoundException {
		String msg = orderService.saveOrder(orderDTO);
		return new ResponseEntity<String>(msg, HttpStatus.CREATED);
	}


	@GetMapping("/{orderId}")
	public ResponseEntity<OrderDTO> getByOrderId(@PathVariable int orderId) throws ResourceNotFoundException {
		return new ResponseEntity<OrderDTO>(orderService.getByOrderId(orderId), HttpStatus.OK);
	}

	@PutMapping("/{orderId}/status")
	public ResponseEntity<String> updateOrderStatus(@PathVariable int orderId,@Valid @RequestBody OrderDTO orderDTO) throws ResourceNotFoundException {
		return new ResponseEntity<String>(orderService.updateOrderStatus(orderId, orderDTO), HttpStatus.OK);
	}

	@DeleteMapping("/{orderId}")
	public ResponseEntity<String> deleteOrder(@PathVariable int orderId) throws ResourceNotFoundException {
		String msg = orderService.deleteOrder(orderId);
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}
	
	 @GetMapping("/restaurants/{restaurantId}")
	    public ResponseEntity<List<OrderDTO>> getOrdersByRestaurantId(@PathVariable int restaurantId) {
	        List<OrderDTO> orders = orderService.getOrdersByRestaurantId(restaurantId);
	        return new ResponseEntity<>(orders, HttpStatus.OK);
	   }
}
