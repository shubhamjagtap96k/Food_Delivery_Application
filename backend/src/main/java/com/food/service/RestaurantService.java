package com.food.service;

import java.util.ArrayList;

import java.util.List;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.dao.IMenuRepo;
import com.food.dao.IRatingRepo;
import com.food.dao.IRestaurantRepo;
import com.food.dto.RestaurantDTO;
import com.food.exception.ResourceNotFoundException;
import com.food.model.MenuItem;
import com.food.model.Rating;
import com.food.model.Restaurant;

@Service
public class RestaurantService implements IRestaurantService {
	@Autowired
	IRestaurantRepo restaurantRepo;
	
	@Autowired
	IMenuRepo menuRepo;
	
	@Autowired
	IRatingRepo ratingRepo;
	
	@Autowired
	private ModelMapper model;
	
	
	public List<RestaurantDTO> getAllRestaurants()throws ResourceNotFoundException {
		List<RestaurantDTO> restaurantDtoList = new ArrayList<>();
		List<Restaurant> restaurantList=restaurantRepo.findAll();
		if(restaurantList.isEmpty()) {
			throw new ResourceNotFoundException("No Restaurants Available");
		} else {
//			ModelMapper model = new ModelMapper();
			for(Restaurant r: restaurantList) {
				RestaurantDTO rdto = new RestaurantDTO();
				model.map(r, rdto);
				restaurantDtoList.add(rdto);
			}
		}
		return restaurantDtoList;
	}
	
	public RestaurantDTO getByRestaurantId(int id) throws ResourceNotFoundException{
		Optional<Restaurant> restaurant = restaurantRepo.findById(id);
		if(restaurant.isPresent()) {
//			ModelMapper model = new ModelMapper();
			RestaurantDTO rdto = new RestaurantDTO();
			model.map(restaurant.get(), rdto);
			return rdto;
		}
		else
			throw new ResourceNotFoundException("Restaurant with id "+id+" does not exist");
	}
	
	public String addRestaurant(RestaurantDTO restaurantdto) throws ResourceNotFoundException {
		Restaurant restaurant = new Restaurant();
//		ModelMapper model = new ModelMapper();
		model.map(restaurantdto, restaurant);
		Optional<Restaurant> opt = restaurantRepo.findByRestaurantName(restaurant.getRestaurantName());
		if(opt.isPresent()) {
			throw new ResourceNotFoundException("Restaurant already exists..");
		}else {
			restaurantRepo.save(restaurant);
			return "Restaurant created successfully";
		}
	}
	

	public String deleteRestaurant(int id) throws ResourceNotFoundException {
		if (!restaurantRepo.existsById(id)) {
			throw new ResourceNotFoundException("Restaurant with id " + id + " does not exist");
		}
		    
		restaurantRepo.deleteById(id);
		return "Restaurant deleted successfully";
	}

	
	public String updateRestaurant(int id, RestaurantDTO restaurantdto) throws ResourceNotFoundException {
		Restaurant restaurant = new Restaurant();
//		ModelMapper model = new ModelMapper();
		Optional<Restaurant> res = restaurantRepo.findById(restaurantdto.getRestaurantId());
		if(res.isPresent()) {
			model.map(restaurantdto, restaurant);
			restaurantRepo.save(restaurant);
			return "Restaurant details updated successfully";
		} else {
			throw new ResourceNotFoundException("Restaurant with id " + id + " does not exist");
		}
		
		
	}
	
	
	public List<MenuItem> getMenuItemsForRestaurant(int restaurantId) throws ResourceNotFoundException {
		List<MenuItem> menuList = menuRepo.findByRestaurantRestaurantId(restaurantId);
		if(menuList.isEmpty()) {
			throw new ResourceNotFoundException("No Menu Available");
		}
		return menuList;
	}
	
	public List<Rating> getRatingForRestaurant(int restaurantId) throws ResourceNotFoundException {
		List<Rating> ratingList = ratingRepo.findByRestaurantRestaurantId(restaurantId);
		if(ratingList.isEmpty()) {
			throw new ResourceNotFoundException("No Review Available");
		}
		return ratingList;
	}
	
    public List<String> getDeliveryAreasForRestaurant(Integer restaurantId) throws ResourceNotFoundException {
        Optional<Restaurant> restaurant = restaurantRepo.findById(restaurantId);
        if(restaurant.isPresent()) {
        	return restaurant.get().getDeliveryAreas();
        } else {
        	throw new ResourceNotFoundException("Restaurant with id " + restaurantId + " does not exist");
        }
    }
    
    public Restaurant findByUsernameAndPassword(String username, String password) {
        return restaurantRepo.findByRestaurantNameAndPassword(username, password);
    }
	
}
