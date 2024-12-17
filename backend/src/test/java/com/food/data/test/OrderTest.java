package com.food.data.test;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
 
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
 
import com.food.dao.IOrderRepo;
import com.food.dto.OrderDTO;
import com.food.dto.OrderItemDTO;
import com.food.exception.ResourceNotFoundException;
import com.food.model.Customer;
import com.food.model.DeliveryDriver;
import com.food.model.MenuItem;
import com.food.model.Order;
import com.food.model.OrderItem;
import com.food.model.Restaurant;
import com.food.service.OrderService;
 
public class OrderTest {
 
    @Mock
    private IOrderRepo orderRepo;
 
    @Mock
    private ModelMapper modelMapper;
 
    @InjectMocks
    private OrderService orderService;
 
    private Order order;
    private OrderDTO orderDTO;
    private OrderItem orderItem;
    private OrderItemDTO orderItemDTO;
    private MenuItem menuItem;
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
 
        // Initialize sample data for testing
        menuItem = new MenuItem();
        menuItem.setItemName("Pizza");
        menuItem.setItemPrice(BigDecimal.valueOf(10.00));
 
        orderItem = new OrderItem();
        orderItem.setOrderItemId(1);
        orderItem.setQuantity(2);
        orderItem.setMenuItem(menuItem);
 
        orderItemDTO = new OrderItemDTO();
        orderItemDTO.setOrderItemId(1);
        orderItemDTO.setQuantity(2);
        orderItemDTO.setItemName("Pizza");
        orderItemDTO.setPrice(BigDecimal.valueOf(20.00)); // 10.00 * 2
 
        order = new Order();
        order.setOrderId(1);
        order.setOrderDate(LocalDateTime.now());
        order.setCustomers(new Customer()); // Mock or initialize as needed
        order.setRestaurant(new Restaurant()); // Mock or initialize as needed
        order.setDeliveryDriver(new DeliveryDriver()); // Mock or initialize as needed
        order.setOrderItems(Arrays.asList(orderItem));
 
        orderDTO = new OrderDTO();
        orderDTO.setOrderId(1);
        orderDTO.setOrderDate(LocalDateTime.now());
        orderDTO.setCustomerId(1);
        orderDTO.setRestaurantId(1);
        orderDTO.setDeliveryDriverId(1);
        orderDTO.setOrderStatus("Pending");
        orderDTO.setOrderItems(Arrays.asList(orderItemDTO));
    }
 
    @Test
    void testSaveOrder_Success() throws ResourceNotFoundException {
        OrderDTO orderDTO = new OrderDTO();
        Order order = new Order();
        when(modelMapper.map(orderDTO, Order.class)).thenReturn(order);
 
        String result = orderService.saveOrder(orderDTO);
 
        assertEquals("Order saved successfully", result);
        verify(orderRepo).save(order);
    }
 
 
    @Test
    void testGetByOrderId_NotFound() {
        when(orderRepo.findById(1)).thenReturn(Optional.empty());
 
        assertThrows(ResourceNotFoundException.class, () -> orderService.getByOrderId(1));
    }
 
    @Test
    void testDeleteOrder_Success() throws ResourceNotFoundException {
        when(orderRepo.existsById(1)).thenReturn(true);
 
        String result = orderService.deleteOrder(1);
 
        assertEquals("Order deleted successfully", result);
        verify(orderRepo).deleteById(1);
    }
 
    @Test
    void testDeleteOrder_NotFound() {
        when(orderRepo.existsById(1)).thenReturn(false);
 
        assertThrows(ResourceNotFoundException.class, () -> orderService.deleteOrder(1));
    }
 
    @Test
    void testUpdateOrderStatus_Success() throws ResourceNotFoundException {
        when(orderRepo.findById(1)).thenReturn(Optional.of(order));
 
        String result = orderService.updateOrderStatus(1, orderDTO);
 
        assertEquals("Order updated successfully", result);
        verify(orderRepo).save(order);
    }
 
    @Test
    void testUpdateOrderStatus_NotFound() {
        when(orderRepo.findById(1)).thenReturn(Optional.empty());
 
        assertThrows(ResourceNotFoundException.class, () -> orderService.updateOrderStatus(1, orderDTO));
    }
 
    
}