package com.food.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.food.dao.IOrderRepo;
import com.food.dto.OrderDTO;
import com.food.dto.OrderItemDTO;
import com.food.exception.ResourceNotFoundException;
import com.food.model.Order;
import com.food.model.OrderItem;
import com.food.model.MenuItem;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderService implements IOrderService {

    @Autowired
    IOrderRepo orderRepo;

    @Autowired
    private ModelMapper model;

    @Override
    public String saveOrder(OrderDTO orderDTO) throws ResourceNotFoundException {
        Order order = new Order();
        model.map(orderDTO, order);
        orderRepo.save(order);
        return "Order saved successfully";
    }
    
    
    @Override
    public OrderDTO getByOrderId(int id) throws ResourceNotFoundException {
        Order order = orderRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Order with id " + id + " does not exist"));

        // Create OrderDTO and map common fields
        OrderDTO orderDto = new OrderDTO();
        model.map(order, orderDto);

        // Manually map nested entities to DTO fields
        if (order.getCustomers() != null) {
            orderDto.setCustomerId(order.getCustomers().getCustomerId());
        }
        if (order.getRestaurant() != null) {
            orderDto.setRestaurantId(order.getRestaurant().getRestaurantId());
        }
        if (order.getDeliveryDriver() != null) {
            orderDto.setDeliveryDriverId(order.getDeliveryDriver().getDriverId());
        }

        // Map OrderItems to OrderItemDTO
        List<OrderItemDTO> orderItemDTOs = order.getOrderItems().stream()
            .map(orderItem -> {
                OrderItemDTO orderItemDTO = new OrderItemDTO();
                orderItemDTO.setOrderItemId(orderItem.getOrderItemId());
                orderItemDTO.setQuantity(orderItem.getQuantity());

                MenuItem menuItem = orderItem.getMenuItem();
                if (menuItem != null) {
                    orderItemDTO.setItemName(menuItem.getItemName());

                    // Correct conversion and calculation
                    BigDecimal itemPrice = menuItem.getItemPrice(); // Convert from BigDecimal
                    BigDecimal quantity = new BigDecimal(orderItem.getQuantity()); // Convert from Integer
                    BigDecimal totalPrice = itemPrice.multiply(quantity); // Calculate total price

                    orderItemDTO.setPrice(totalPrice); // Set total price
                }

                return orderItemDTO;
            })
            .collect(Collectors.toList());

        orderDto.setOrderItems(orderItemDTOs);
        return orderDto;
    }


    @Override
    public String deleteOrder(int id) throws ResourceNotFoundException {
        if (!orderRepo.existsById(id)) {
            throw new ResourceNotFoundException("Order with id " + id + " does not exist");
        }
        orderRepo.deleteById(id);
        return "Order deleted successfully";
    }

    @Override
    public String updateOrderStatus(int id, OrderDTO orderDTO) throws ResourceNotFoundException {
        return orderRepo.findById(id)
            .map(order -> {
                order.setOrderStatus(orderDTO.getOrderStatus());
                orderRepo.save(order);
                return "Order updated successfully";
            })
            .orElseThrow(() -> new ResourceNotFoundException("Order with id " + id + " does not exist"));
    }

    @Override
    public List<OrderDTO> getOrdersByRestaurantId(int restaurantId) throws ResourceNotFoundException {
        List<Order> orders = orderRepo.findByRestaurantRestaurantId(restaurantId);
        return orders.stream()
            .map(order -> {
                OrderDTO orderDto = new OrderDTO();
                model.map(order, orderDto);

                // Manually map nested entities to DTO fields
                if (order.getCustomers() != null) {
                    orderDto.setCustomerId(order.getCustomers().getCustomerId());
                }
                if (order.getRestaurant() != null) {
                    orderDto.setRestaurantId(order.getRestaurant().getRestaurantId());
                }
                if (order.getDeliveryDriver() != null) {
                    orderDto.setDeliveryDriverId(order.getDeliveryDriver().getDriverId());
                }

                // Map OrderItems to OrderItemDTO
                List<OrderItemDTO> orderItemDTOs = order.getOrderItems().stream()
                    .map(orderItem -> {
                        OrderItemDTO orderItemDTO = new OrderItemDTO();
                        orderItemDTO.setOrderItemId(orderItem.getOrderItemId());
                        orderItemDTO.setQuantity(orderItem.getQuantity());

                        MenuItem menuItem = orderItem.getMenuItem();
                        if (menuItem != null) {
                            orderItemDTO.setItemName(menuItem.getItemName());

                            // Correct conversion and calculation
                            BigDecimal itemPrice = menuItem.getItemPrice(); // Convert from BigDecimal
                            BigDecimal quantity = new BigDecimal(orderItem.getQuantity()); // Convert from Integer
                            BigDecimal totalPrice = itemPrice.multiply(quantity); // Calculate total price

                            orderItemDTO.setPrice(totalPrice); // Set total price
                        }

                        return orderItemDTO;
                    })
                    .collect(Collectors.toList());

                orderDto.setOrderItems(orderItemDTOs);
                return orderDto;
            })
            .collect(Collectors.toList());
    }

}
