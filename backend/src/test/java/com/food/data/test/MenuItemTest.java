package com.food.data.test;
 
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
 
import com.food.dao.IMenuRepo;
import com.food.dao.IRestaurantRepo;
import com.food.dto.MenuItemDTO;
import com.food.exception.ResourceNotFoundException;
import com.food.model.MenuItem;
import com.food.model.Restaurant;
import com.food.service.MenuItemService;
 
import org.modelmapper.ModelMapper;
 
@ExtendWith(MockitoExtension.class)
public class MenuItemTest {
 
	@Mock
	private IMenuRepo menuRepo;
 
	@Mock
	private IRestaurantRepo restaurantRepo;
 
	@Mock
	private ModelMapper modelMapper;
 
	@InjectMocks
	private MenuItemService menuItemService;
 
	private MenuItem menuItem;
	private MenuItemDTO menuItemDTO;
	private Restaurant restaurant;
 
	@BeforeEach
	void setUp() {
		restaurant = new Restaurant();
		restaurant.setRestaurantId(1);
		restaurant.setRestaurantName("Test Restaurant");
 
		menuItem = new MenuItem();
		menuItem.setItemId(1);
		menuItem.setItemName("Burger");
		menuItem.setItemDescription("Delicious Burger");
		menuItem.setItemPrice(BigDecimal.valueOf(5.99));
		//menuItem.setImage("burger.jpg");
		menuItem.setRestaurant(restaurant);
 
		menuItemDTO = new MenuItemDTO();
		menuItemDTO.setItemId(1);
		menuItemDTO.setItemName("Burger");
		menuItemDTO.setItemDescription("Delicious Burger");
		menuItemDTO.setItemPrice(BigDecimal.valueOf(5.99));
		//menuItemDTO.setImage("burger.jpg");
		menuItemDTO.setRestaurantId(1);
	}
 
 
	@Test
	void testGetAllMenuItems_Empty() {
		when(menuRepo.findByRestaurantRestaurantId(1)).thenReturn(new ArrayList<>());
 
		ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
			menuItemService.getAllMenuItems(1);
		});
 
		assertEquals("No Menu Items Available", thrown.getMessage());
	}
 
	@Test
	void testAddMenuItem_Success() {
		when(restaurantRepo.findById(1)).thenReturn(Optional.of(restaurant));
		when(modelMapper.map(menuItemDTO, MenuItem.class)).thenReturn(menuItem);
 
		String result = menuItemService.addMenuItem(1, menuItemDTO);
 
		assertEquals("Menu item added to the restaurant successfully", result);
		verify(menuRepo, times(1)).save(menuItem);
	}
 
	@Test
	void testAddMenuItem_RestaurantNotFound() {
		when(restaurantRepo.findById(1)).thenReturn(Optional.empty());
 
		ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
			menuItemService.addMenuItem(1, menuItemDTO);
		});
 
		assertEquals("Restaurant not found", thrown.getMessage());
	}
 
	@Test
	void testUpdateMenuItem_RestaurantNotFound() {
		when(restaurantRepo.findById(1)).thenReturn(Optional.empty());
 
		ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
			menuItemService.updateMenuItem(1, 1, menuItemDTO);
		});
 
		assertEquals("Restaurant not found", thrown.getMessage());
	}
 
	@Test
	void testUpdateMenuItem_MenuItemNotFound() {
		when(restaurantRepo.findById(1)).thenReturn(Optional.of(restaurant));
		when(menuRepo.findById(1)).thenReturn(Optional.empty());
 
		ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
			menuItemService.updateMenuItem(1, 1, menuItemDTO);
		});
 
		assertEquals("Menu item not found", thrown.getMessage());
	}
 
 
	@Test
	void testDeleteMenuItem_Success() {
		when(restaurantRepo.findById(1)).thenReturn(Optional.of(restaurant));
		when(menuRepo.findById(1)).thenReturn(Optional.of(menuItem));
 
		String result = menuItemService.deleteMenuItem(1, 1);
 
		assertEquals("Menu item deleted successfully", result);
		verify(menuRepo, times(1)).delete(menuItem);
	}
 
	@Test
	void testDeleteMenuItem_RestaurantNotFound() {
		when(restaurantRepo.findById(1)).thenReturn(Optional.empty());
 
		ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
			menuItemService.deleteMenuItem(1, 1);
		});
 
		assertEquals("Restaurant not found", thrown.getMessage());
	}
 
	@Test
	void testDeleteMenuItem_MenuItemNotFound() {
		when(restaurantRepo.findById(1)).thenReturn(Optional.of(restaurant));
		when(menuRepo.findById(1)).thenReturn(Optional.empty());
 
		ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
			menuItemService.deleteMenuItem(1, 1);
		});
 
		assertEquals("Menu item not found", thrown.getMessage());
	}
 
}
