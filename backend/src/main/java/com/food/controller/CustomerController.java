package com.food.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.food.dto.CustomerDTO;
import com.food.dto.LoginDTO;
import com.food.dto.OrderDTO;
import com.food.dto.RatingDTO;
import com.food.dto.RestaurantDTO;
import com.food.service.ICustomerService;
import com.food.service.IRatingService;
 

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/customers")
public class CustomerController {
 
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IRatingService ratingService;

    
    @PostMapping("/login")
    public ResponseEntity<CustomerDTO> login(@RequestBody LoginDTO loginRequest) {
        CustomerDTO customerDTO = customerService.authenticateCustomer(loginRequest.getUsername(), loginRequest.getPassword());
        if (customerDTO != null) {
            return ResponseEntity.ok(customerDTO);
        } else {
            return ResponseEntity.status(401).build();
        }
    }
    
    
    @PostMapping
    public ResponseEntity<CustomerDTO> saveCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO newCustomer = customerService.saveCustomer(customerDTO);
        return ResponseEntity.ok(newCustomer);
    }
 
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }
 
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Integer customerId) {
        CustomerDTO customerDTO = customerService.getCustomerById(customerId);
        return ResponseEntity.ok(customerDTO);
    }
 
    @PutMapping("/{customerId}")
    public String updateCustomer(@PathVariable Integer customerId, @RequestBody CustomerDTO customerDTO) {
        customerDTO.setCustomerId(customerId);
        CustomerDTO updatedCustomer = customerService.updateCustomer(customerDTO);
        return "Customer Updated succefully !!";  //ResponseEntity.ok(updatedCustomer);
    }
 
    @DeleteMapping("/{customerId}")
    public  String deleteCustomer(@PathVariable Integer customerId) {
        customerService.deleteCustomer(customerId);
      return "Customer Deleted";
    }

   
    @GetMapping("/{customerId}/reviews")
    public ResponseEntity<List<RatingDTO>> getCustomerReviews(@PathVariable Integer customerId) {
        List<RatingDTO> reviews = customerService.getCustomerReviews(customerId);
        return ResponseEntity.ok(reviews);
    }

    
    
    @GetMapping("/{customerId}/orders")
    public ResponseEntity<List<OrderDTO>> getOrdersByCustomerId(@PathVariable Integer customerId) {
        List<OrderDTO> orders = customerService.getOrdersByCustomerId(customerId);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/{customerId}/favoriteRestaurants")
    public ResponseEntity<CustomerDTO> addFavoriteRestaurant(@PathVariable Integer customerId, @RequestBody RestaurantDTO restaurantDTO) {
        CustomerDTO updatedCustomer = customerService.addFavoriteRestaurant(customerId, restaurantDTO);
        return ResponseEntity.ok(updatedCustomer);
    }
 
    @DeleteMapping("/{customerId}/favorites/{restaurantId}")
    public ResponseEntity<String> removeFavoriteRestaurant(
            @PathVariable Integer customerId, 
            @PathVariable Integer restaurantId) {
        customerService.removeFavoriteRestaurant(customerId, restaurantId);
        return ResponseEntity.ok("Restaurant removed from customer's favorites successfully.");
    }

}
