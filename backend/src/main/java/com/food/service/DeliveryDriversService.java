package com.food.service;
 
import java.util.List;
import java.util.stream.Collectors;
 
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.food.dao.IDeliveryDriverRepo;
import com.food.dao.IOrderRepo;
import com.food.dto.DeliveryDriversDto;
import com.food.exception.ResourceNotFoundException;
import com.food.model.DeliveryDriver;
import com.food.model.Order;

import jakarta.transaction.Transactional;
 
@Service
public class DeliveryDriversService implements IDeliveryDriversService {
 
    @Autowired
    private IDeliveryDriverRepo deliveryDriversRepository;
 
    @Autowired
    private IOrderRepo orderRepo;
   
    @Autowired
    private ModelMapper modelMapper;
 
    @Override
    public List<DeliveryDriversDto> getAllDrivers() {
        List<DeliveryDriver> drivers = deliveryDriversRepository.findAll();
        
        if (drivers.isEmpty()) {
            throw new ResourceNotFoundException("No delivery drivers available.");
        }
        
        return drivers.stream()
                .map(driver -> modelMapper.map(driver, DeliveryDriversDto.class))
                .collect(Collectors.toList());
    }
 
    @Override
    public DeliveryDriversDto getDriverById(Integer driverId) {
        DeliveryDriver driver = deliveryDriversRepository.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found with ID: " + driverId));
        return modelMapper.map(driver, DeliveryDriversDto.class);
    }
                                                  
    @Override
    public List<Order> getOrdersByDriverId(Integer driverId) {
        if (!deliveryDriversRepository.existsById(driverId)) {
            throw new ResourceNotFoundException("Driver not found with ID: " + driverId);
        }
        return orderRepo.findByDeliveryDriverDriverId(driverId);
    }
 
//    @Override
//    public void assignDriverToOrder(Integer orderId, Integer driverId) {
//        Order order = orderRepo.findById(orderId)
//                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));
//        DeliveryDriver driver = deliveryDriversRepository.findById(driverId)
//                .orElseThrow(() -> new ResourceNotFoundException("Driver not found with ID: " + driverId));
//        order.setDeliveryDriver(driver);
//        orderRepo.save(order);
//    }
    
    @Transactional
    @Override
    public void assignDriverToOrder(Integer orderId, Integer driverId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));
        DeliveryDriver driver = deliveryDriversRepository.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found with ID: " + driverId));
        order.setDeliveryDriver(driver);
        orderRepo.save(order);
    }
 
    
    @Override
    public DeliveryDriversDto updateDeliveryDriverLocationById(int driverId, String driverLocation) {
        DeliveryDriver deliveryDriver = deliveryDriversRepository.findByDriverId(driverId);
        
        if (deliveryDriver == null) {
            throw new ResourceNotFoundException("Delivery driver not found with ID: " + driverId);
        }
        
        deliveryDriver.setDriverLocation(driverLocation); // This should be a plain string
        deliveryDriversRepository.save(deliveryDriver);
        
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(deliveryDriver, DeliveryDriversDto.class);
    }
 
}