package com.food.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.food.dto.DeliveryDriversDto;
import com.food.exception.ResourceNotFoundException;
import com.food.model.Order;
import com.food.service.IDeliveryDriversService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/drivers")
public class DeliveryDriversController {

    @Autowired
    private IDeliveryDriversService deliveryDriversService;

    // Get all drivers
    @GetMapping
    public ResponseEntity<List<DeliveryDriversDto>> getAllDrivers() {
        List<DeliveryDriversDto> drivers = deliveryDriversService.getAllDrivers();
        return ResponseEntity.ok(drivers);
    }

    // Get a specific driver by ID
    @GetMapping("/{driverId}")
    public ResponseEntity<DeliveryDriversDto> getDriverById(@PathVariable Integer driverId) throws ResourceNotFoundException {
        DeliveryDriversDto driver = deliveryDriversService.getDriverById(driverId);
        return ResponseEntity.ok(driver);
    }

    // Assign a driver to an order
    @PutMapping("/orders/{orderId}/assignDriver/{driverId}")
    public ResponseEntity<String> assignDriverToOrder(@PathVariable Integer orderId, @PathVariable Integer driverId) throws ResourceNotFoundException {
        deliveryDriversService.assignDriverToOrder(orderId, driverId);
        return ResponseEntity.ok("Driver assigned to the order successfully");
    }

    // Update the location of a driver
    @PutMapping("/{driverId}/location")
    public ResponseEntity<String> updateDriverLocation(
            @PathVariable Integer driverId,
            @RequestBody Map<String, String> locationData) throws ResourceNotFoundException {
        
        String driverLocation = locationData.get("driverLocation");
        deliveryDriversService.updateDeliveryDriverLocationById(driverId, driverLocation);
        
        return ResponseEntity.ok("Driver location updated successfully");
    }

    // Get orders assigned to a driver
    @GetMapping("/{driverId}/orders")
    public ResponseEntity<List<Order>> getOrdersByDriverId(@PathVariable Integer driverId) throws ResourceNotFoundException {
        List<Order> orders = deliveryDriversService.getOrdersByDriverId(driverId);
        return ResponseEntity.ok(orders);
    }
}
