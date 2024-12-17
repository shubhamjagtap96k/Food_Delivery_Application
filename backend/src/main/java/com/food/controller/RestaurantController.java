package com.food.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.food.dao.IRatingRepo;
import com.food.dto.RestaurantDTO;
import com.food.exception.ResourceNotFoundException;
import com.food.model.MenuItem;
import com.food.model.Rating;
import com.food.model.Restaurant;
import com.food.service.IRatingService;
import com.food.service.IRestaurantService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

	@Autowired
	IRestaurantService restaurantService;
	
	@Autowired
	IRatingService ratingService;
	
	@GetMapping
	public ResponseEntity<List<RestaurantDTO>> getRestaurants() throws ResourceNotFoundException {
		return new ResponseEntity<List<RestaurantDTO>>(restaurantService.getAllRestaurants(), HttpStatus.OK);
	}
	
	@GetMapping("/{restaurantId}")
	public ResponseEntity<RestaurantDTO> getByRestaurantId(@PathVariable int restaurantId) throws ResourceNotFoundException {
		return new ResponseEntity<RestaurantDTO>(restaurantService.getByRestaurantId(restaurantId), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<String> saveRestaurant(@RequestBody RestaurantDTO restaurantdto) throws ResourceNotFoundException {
	    return new ResponseEntity<String>(restaurantService.addRestaurant(restaurantdto), HttpStatus.OK);
	}
	
	@PutMapping("/{restaurantId}")
	public ResponseEntity<String> updateRestaurant(@PathVariable int restaurantId, @RequestBody RestaurantDTO restaurantdto) throws ResourceNotFoundException {
		return new ResponseEntity<String>(restaurantService.updateRestaurant(restaurantId, restaurantdto), HttpStatus.OK);

	}

	@DeleteMapping("/{restaurantId}")
	public ResponseEntity<String> deleteRestaurant(@PathVariable int restaurantId) throws ResourceNotFoundException {
		String msg = restaurantService.deleteRestaurant(restaurantId);
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}
	
//	@GetMapping("/{restaurantId}/menu")
//	public ResponseEntity<List<MenuItem>> getMenuItems(@PathVariable int restaurantId) throws ResourceNotFoundException {
//		return new ResponseEntity<List<MenuItem>> (restaurantService.getMenuItemsForRestaurant(restaurantId), HttpStatus.OK);
//	}
	
	@GetMapping("/{restaurantId}/reviews")
	public ResponseEntity<List<Rating>> getRatings(@PathVariable int restaurantId) throws ResourceNotFoundException {
		return new ResponseEntity<List<Rating>> (ratingService.getReviewsByRestaurantId(restaurantId), HttpStatus.OK);
	}
	
	 @GetMapping("/{restaurantId}/delivery-areas")
	 public ResponseEntity<List<String>> getDeliveryAreas(@PathVariable Integer restaurantId) throws ResourceNotFoundException {
	     List<String> deliveryAreas = restaurantService.getDeliveryAreasForRestaurant(restaurantId);
	     return new ResponseEntity<>(deliveryAreas, HttpStatus.OK);
	 }
	 
	    @PostMapping("/login")
	    public ResponseEntity<?> loginRestaurant(@RequestBody RestaurantDTO restaurant) {
	        Restaurant existingRestaurant = restaurantService.findByUsernameAndPassword(restaurant.getRestaurantName(), restaurant.getPassword());
	        if (existingRestaurant != null) {
	            return new ResponseEntity<>(existingRestaurant, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
	        }
	    }
}
