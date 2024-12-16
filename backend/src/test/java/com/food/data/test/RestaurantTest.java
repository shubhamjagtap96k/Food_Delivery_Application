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
 
import com.food.dao.IMenuRepo;
import com.food.dao.IRatingRepo;
import com.food.dao.IRestaurantRepo;
import com.food.dto.RestaurantDTO;
import com.food.exception.ResourceNotFoundException;
import com.food.model.MenuItem;
import com.food.model.Rating;
import com.food.model.Restaurant;
import com.food.service.RestaurantService;
import org.modelmapper.ModelMapper;
 
@ExtendWith(MockitoExtension.class)
public class RestaurantTest {
 
    @Mock
    private IRestaurantRepo restaurantRepo;
 
    @Mock
    private IMenuRepo menuRepo;
 
    @Mock
    private IRatingRepo ratingRepo;
 
    @Mock
    private ModelMapper model;
 
    @InjectMocks
    private RestaurantService restaurantService;
 
    private Restaurant restaurant;
    private RestaurantDTO restaurantDTO;
 
    @BeforeEach
    void setUp() {
        restaurant = new Restaurant();
        restaurant.setRestaurantId(1);
        restaurant.setRestaurantName("Test Restaurant");
        restaurant.setRestaurantAddress("123 Test Street");
        restaurant.setRestaurantPhone("1234567890");
        restaurant.setDeliveryAreas(List.of("Area1", "Area2"));
 
        restaurantDTO = new RestaurantDTO();
        restaurantDTO.setRestaurantId(1);
        restaurantDTO.setRestaurantName("Test Restaurant");
        restaurantDTO.setRestaurantAddress("123 Test Street");
        restaurantDTO.setRestaurantPhone("1234567890");
        restaurantDTO.setDeliveryAreas(List.of("Area1", "Area2"));
    }
 
    @Test
    void testGetByRestaurantId_NotFound() {
        when(restaurantRepo.findById(1)).thenReturn(Optional.empty());
 
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            restaurantService.getByRestaurantId(1);
        });
 
        assertEquals("Restaurant with id 1 does not exist", thrown.getMessage());
    }
 

 
    @Test
    void testDeleteRestaurant_Success() {
        when(restaurantRepo.existsById(1)).thenReturn(true);
 
        String result = restaurantService.deleteRestaurant(1);
 
        assertEquals("Restaurant deleted successfully", result);
    }
 
    @Test
    void testDeleteRestaurant_NotFound() {
        when(restaurantRepo.existsById(1)).thenReturn(false);
 
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            restaurantService.deleteRestaurant(1);
        });
 
        assertEquals("Restaurant with id 1 does not exist", thrown.getMessage());
    }

    @Test
    void testUpdateRestaurant_NotFound() {
        when(restaurantRepo.findById(1)).thenReturn(Optional.empty());
 
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            restaurantService.updateRestaurant(1, restaurantDTO);
        });
 
        assertEquals("Restaurant with id 1 does not exist", thrown.getMessage());
    }
 
    @Test
    void testGetMenuItemsForRestaurant_Success() {
        List<MenuItem> menuItems = List.of(new MenuItem()); // Add appropriate MenuItem setup
        when(menuRepo.findByRestaurantRestaurantId(1)).thenReturn(menuItems);
 
        List<MenuItem> result = restaurantService.getMenuItemsForRestaurant(1);
 
        assertNotNull(result);
        assertEquals(menuItems.size(), result.size());
    }
 
    @Test
    void testGetMenuItemsForRestaurant_Empty() {
        when(menuRepo.findByRestaurantRestaurantId(1)).thenReturn(new ArrayList<>());
 
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            restaurantService.getMenuItemsForRestaurant(1);
        });
 
        assertEquals("No Menu Available", thrown.getMessage());
    }
 
    @Test
    void testGetRatingForRestaurant_Success() {
        List<Rating> ratings = List.of(new Rating()); // Add appropriate Rating setup
        when(ratingRepo.findByRestaurantRestaurantId(1)).thenReturn(ratings);
 
        List<Rating> result = restaurantService.getRatingForRestaurant(1);
 
        assertNotNull(result);
        assertEquals(ratings.size(), result.size());
    }
 
    @Test
    void testGetRatingForRestaurant_Empty() {
        when(ratingRepo.findByRestaurantRestaurantId(1)).thenReturn(new ArrayList<>());
 
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            restaurantService.getRatingForRestaurant(1);
        });
 
        assertEquals("No Review Available", thrown.getMessage());
    }
 
    @Test
    void testGetDeliveryAreasForRestaurant_Success() {
        when(restaurantRepo.findById(1)).thenReturn(Optional.of(restaurant));
 
        List<String> result = restaurantService.getDeliveryAreasForRestaurant(1);
 
        assertNotNull(result);
        assertEquals(restaurant.getDeliveryAreas(), result);
    }
 
    @Test
    void testGetDeliveryAreasForRestaurant_NotFound() {
        when(restaurantRepo.findById(1)).thenReturn(Optional.empty());
 
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            restaurantService.getDeliveryAreasForRestaurant(1);
        });
 
        assertEquals("Restaurant with id 1 does not exist", thrown.getMessage());
    }
}