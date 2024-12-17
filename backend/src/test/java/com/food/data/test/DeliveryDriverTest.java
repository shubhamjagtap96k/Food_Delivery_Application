package com.food.data.test;
 
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
 
import com.food.dao.IDeliveryDriverRepo;
import com.food.dao.IOrderRepo;
import com.food.dto.DeliveryDriversDto;
import com.food.exception.ResourceNotFoundException;
import com.food.model.DeliveryDriver;
import com.food.model.Order;
import com.food.service.DeliveryDriversService;
 
import org.modelmapper.ModelMapper;
 
@ExtendWith(MockitoExtension.class)
public class DeliveryDriverTest {
 
	@Mock
	private IDeliveryDriverRepo deliveryDriverRepo;
 
	@Mock
	private IOrderRepo orderRepo;
 
	@Mock
	private ModelMapper modelMapper;
 
	@InjectMocks
	private DeliveryDriversService deliveryDriversService;
 
	private DeliveryDriver deliveryDriver;
	private DeliveryDriversDto deliveryDriversDto;
	private Order order;
 
	@BeforeEach
	void setUp() {
		deliveryDriver = new DeliveryDriver();
		deliveryDriver.setDriverId(1);
		deliveryDriver.setDriverName("John Doe");
		deliveryDriver.setDriverPhone("1234567890");
		deliveryDriver.setDriverVehicle("Bike");
		deliveryDriver.setDriverLocation("Location A");
 
		deliveryDriversDto = new DeliveryDriversDto();
		deliveryDriversDto.setDriverId(1);
		deliveryDriversDto.setDriverName("John Doe");
		deliveryDriversDto.setDriverPhone("1234567890");
		deliveryDriversDto.setDriverVehicle("Bike");
		deliveryDriversDto.setDriverLocation("Location A");
 
		order = new Order();
		order.setOrderId(1);
		order.setDeliveryDriver(deliveryDriver);
	}
 
	@Test
	void testGetAllDrivers_Success() {
		List<DeliveryDriver> driverList = List.of(deliveryDriver);
		when(deliveryDriverRepo.findAll()).thenReturn(driverList);
		when(modelMapper.map(deliveryDriver, DeliveryDriversDto.class)).thenReturn(deliveryDriversDto);
 
		List<DeliveryDriversDto> result = deliveryDriversService.getAllDrivers();
 
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(deliveryDriversDto.getDriverName(), result.get(0).getDriverName());
	}
 
	@Test
	void testGetAllDrivers_Empty() {
		when(deliveryDriverRepo.findAll()).thenReturn(new ArrayList<>());
 
		ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
			deliveryDriversService.getAllDrivers();
		});
 
		assertEquals("No delivery drivers available.", thrown.getMessage());
	}
 
	@Test
	void testGetDriverById_Success() {
		when(deliveryDriverRepo.findById(1)).thenReturn(Optional.of(deliveryDriver));
		when(modelMapper.map(deliveryDriver, DeliveryDriversDto.class)).thenReturn(deliveryDriversDto);
 
		DeliveryDriversDto result = deliveryDriversService.getDriverById(1);
 
		assertNotNull(result);
		assertEquals(deliveryDriversDto.getDriverName(), result.getDriverName());
	}
 
	@Test
	void testGetDriverById_NotFound() {
		when(deliveryDriverRepo.findById(1)).thenReturn(Optional.empty());
 
		ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
			deliveryDriversService.getDriverById(1);
		});
 
		assertEquals("Driver not found with ID: 1", thrown.getMessage());
	}
 
	@Test
	void testGetOrdersByDriverId_Success() {
		List<Order> orders = List.of(order);
		when(deliveryDriverRepo.existsById(1)).thenReturn(true);
		when(orderRepo.findByDeliveryDriverDriverId(1)).thenReturn(orders);
 
		List<Order> result = deliveryDriversService.getOrdersByDriverId(1);
 
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(order.getOrderId(), result.get(0).getOrderId());
	}
 
	@Test
	void testGetOrdersByDriverId_NotFound() {
		when(deliveryDriverRepo.existsById(1)).thenReturn(false);
 
		ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
			deliveryDriversService.getOrdersByDriverId(1);
		});
 
		assertEquals("Driver not found with ID: 1", thrown.getMessage());
	}
 
	@Test
	void testAssignDriverToOrder_Success() {
		when(orderRepo.findById(1)).thenReturn(Optional.of(order));
		when(deliveryDriverRepo.findById(1)).thenReturn(Optional.of(deliveryDriver));
 
		deliveryDriversService.assignDriverToOrder(1, 1);
 
		verify(orderRepo, times(1)).save(order);
	}
 
	@Test
	void testAssignDriverToOrder_OrderNotFound() {
		when(orderRepo.findById(1)).thenReturn(Optional.empty());
 
		ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
			deliveryDriversService.assignDriverToOrder(1, 1);
		});
 
		assertEquals("Order not found with ID: 1", thrown.getMessage());
	}
 
	@Test
	void testAssignDriverToOrder_DriverNotFound() {
		when(orderRepo.findById(1)).thenReturn(Optional.of(order));
		when(deliveryDriverRepo.findById(1)).thenReturn(Optional.empty());
 
		ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
			deliveryDriversService.assignDriverToOrder(1, 1);
		});
 
		assertEquals("Driver not found with ID: 1", thrown.getMessage());
	}
 
 
	@Test
	void testUpdateDeliveryDriverLocationById_NotFound() {
		when(deliveryDriverRepo.findByDriverId(1)).thenReturn(null);
 
		ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
			deliveryDriversService.updateDeliveryDriverLocationById(1, "New Location");
		});
 
		assertEquals("Delivery driver not found with ID: 1", thrown.getMessage());
	}
}