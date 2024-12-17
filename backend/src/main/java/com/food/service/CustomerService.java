package com.food.service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.dao.CustomerRepository;
import com.food.dao.IOrderRepo;
import com.food.dao.IRatingRepo;
import com.food.dao.IRestaurantRepo;
import com.food.dto.CustomerDTO;
import com.food.dto.OrderDTO;
import com.food.dto.RatingDTO;
import com.food.dto.RestaurantDTO;
import com.food.exception.ResourceNotFoundException;
import com.food.model.Customer;
import com.food.model.DeliveryAddresses;
import com.food.model.Rating;
import com.food.model.Restaurant;

import jakarta.transaction.Transactional;

@Service
public class CustomerService implements ICustomerService {

   @Autowired
   private CustomerRepository customerRepository;
   @Autowired
   private IOrderRepo orderRepository;

   @Autowired
   private IRatingRepo ratingRepository;

   @Autowired
   private IRestaurantRepo restaurantRepository;
   
   @Autowired
   private ModelMapper modelMapper;



   @Override
   public List<CustomerDTO> getAllCustomers() {
       List<Customer> customers = customerRepository.findAll();
       return customers.stream()
                       .map(customer -> modelMapper.map(customer, CustomerDTO.class))
                       .collect(Collectors.toList());
   }

   @Override
   public CustomerDTO getCustomerById(Integer customerId) {
       Customer customer = customerRepository.findById(customerId)
                                            .orElseThrow(() -> new ResourceNotFoundException("Customer with id "+customerId+" is not found"));
       return modelMapper.map(customer, CustomerDTO.class);
   }

   @Override
   public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
       Customer customer = modelMapper.map(customerDTO, Customer.class);
       Customer updatedCustomer = customerRepository.save(customer);
       return modelMapper.map(updatedCustomer, CustomerDTO.class);
   }

   @Override
   public void deleteCustomer(Integer customerId) {
       if (customerRepository.existsById(customerId)) {
           customerRepository.deleteById(customerId);
       } else {
           throw new  ResourceNotFoundException("Customer with id "+customerId+" is not found");
       }
   }

   @Override
   public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
       // Map the CustomerDTO to a Customer entity
       Customer customer = modelMapper.map(customerDTO, Customer.class);

       // If there are delivery addresses, set them separately
       if (customerDTO.getDeliveryAddresses() != null) {
           List<DeliveryAddresses> deliveryAddresses = customerDTO.getDeliveryAddresses().stream()
                   .map(dto -> {
                       DeliveryAddresses address = modelMapper.map(dto, DeliveryAddresses.class);
                       return address;
                   })
                   .collect(Collectors.toList());

           // SetTING the mapped delivery addresses to the customer object
           for (DeliveryAddresses address : deliveryAddresses) {
               address.setCustomers(customer);  // SetTING the customer reference for each address
           }

           customer.setDeliveryAddresses(deliveryAddresses);
       }

       // Save the customer entity with delivery addresses
       customer = customerRepository.save(customer);

       // Return the saved customer as a DTO
       return modelMapper.map(customer, CustomerDTO.class);
   }



//-------------------------------------------------------------------------------------------------//
   public List<OrderDTO> getOrdersByCustomerId(Integer customerId){
       Customer customer = customerRepository.findById(customerId)
               .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

       return customer.getOrders().stream()
               .map(order -> modelMapper.map(order, OrderDTO.class))
               .collect(Collectors.toList());
   }

   @Override
   @Transactional
   public CustomerDTO addFavoriteRestaurant(Integer customerId, RestaurantDTO restaurantDTO) {
       Customer customer = customerRepository.findById(customerId)
               .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

       Restaurant restaurant = restaurantRepository.findById(restaurantDTO.getRestaurantId())
               .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

       Set<Restaurant> favoriteRestaurants = customer.getFavoriteRestaurants();
       if (favoriteRestaurants == null) {
           favoriteRestaurants = new HashSet<>();
       }

       if (!favoriteRestaurants.contains(restaurant)) {
           favoriteRestaurants.add(restaurant);
           customer.setFavoriteRestaurants(favoriteRestaurants);
           customerRepository.save(customer);
       }

       return modelMapper.map(customer, CustomerDTO.class);
   }
//
   @Override
   @Transactional
   public void removeFavoriteRestaurant(Integer customerId, Integer restaurantId) {
       Customer customer = customerRepository.findById(customerId)
               .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));

       Restaurant restaurant = restaurantRepository.findById(restaurantId)
               .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));

       Set<Restaurant> favoriteRestaurants = customer.getFavoriteRestaurants();
       if (favoriteRestaurants != null) {
           boolean isRemoved = favoriteRestaurants.remove(restaurant);
           if (isRemoved) {
               customer.setFavoriteRestaurants(favoriteRestaurants);
               customerRepository.save(customer);
           } else {
               throw new ResourceNotFoundException("Restaurant not found in customer's favorites list.");
           }
       } else {
           throw new ResourceNotFoundException("Restaurant not found in customer's favorites list.");
       }
   }
   @Override
   public List<RatingDTO> getCustomerReviews(Integer customerId) {
       List<Rating> ratings = ratingRepository.findByCustomersCustomerId(customerId);
   	//List<Rating> ratings = customerRepository.findByOrderRatingReview(customerId);
       return ratings.stream()
                     .map(rating -> modelMapper.map(rating, RatingDTO.class))
                     .collect(Collectors.toList());
   }
   
   @Override
   public CustomerDTO authenticateCustomer(String username, String password) {
       Customer customer = customerRepository.findByCustomerName(username);
       if (customer != null && customer.getPassword().equals(password)) {
           return new CustomerDTO(); 
       }
       return null;
   }
}
