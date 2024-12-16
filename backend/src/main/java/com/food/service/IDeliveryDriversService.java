package com.food.service;
 
import java.util.List;
 
import com.food.dto.DeliveryDriversDto;
import com.food.model.Order;
 
public interface IDeliveryDriversService {
    List<DeliveryDriversDto> getAllDrivers();
    DeliveryDriversDto getDriverById(Integer driverId);
    List<Order> getOrdersByDriverId(Integer driverId);
    void assignDriverToOrder(Integer orderId, Integer driverId);
    DeliveryDriversDto updateDeliveryDriverLocationById(int driverId, String driverLocation);
}