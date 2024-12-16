package com.food.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.dao.IMenuRepo;
import com.food.dao.IRestaurantRepo;
import com.food.dto.MenuItemDTO;
import com.food.exception.ResourceNotFoundException;
import com.food.model.MenuItem;
import com.food.model.Restaurant;

@Service
public class MenuItemService implements IMenuItemService {

    @Autowired
    IMenuRepo menuRepo;

    @Autowired
    IRestaurantRepo restaurantRepo;

    @Autowired
    private ModelMapper model;

    public List<MenuItemDTO> getAllMenuItems(int restaurantId) throws ResourceNotFoundException {
        List<MenuItem> menuItems = menuRepo.findByRestaurantRestaurantId(restaurantId);
        List<MenuItemDTO> menuItemDTOList = new ArrayList<>();
        if (menuItems.isEmpty()) {
            throw new ResourceNotFoundException("No Menu Items Available");
        } else {
            for (MenuItem m : menuItems) {
                MenuItemDTO mdto = new MenuItemDTO();
                model.map(m, mdto);
                menuItemDTOList.add(mdto);
            }
        }
        return menuItemDTOList;
    }

    public String addMenuItem(int restaurantId, MenuItemDTO menuItemDTO) throws ResourceNotFoundException {
        Optional<Restaurant> restaurant = restaurantRepo.findById(restaurantId);
        if (restaurant.isPresent()) {
            MenuItem menuItem = model.map(menuItemDTO, MenuItem.class);
            menuItem.setRestaurant(restaurant.get());
            menuRepo.save(menuItem);
            return "Menu item added to the restaurant successfully";
        } else {
            throw new ResourceNotFoundException("Restaurant not found");
        }
    }

    public String updateMenuItem(int restaurantId, int itemId, MenuItemDTO menuItemDTO) throws ResourceNotFoundException {
        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        MenuItem menuItem = menuRepo.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));

        if (!menuItem.getRestaurant().getRestaurantId().equals(restaurantId)) {
            throw new ResourceNotFoundException("Menu item does not belong to the specified restaurant");
        }

        menuItem.setItemName(menuItemDTO.getItemName());
        menuItem.setItemDescription(menuItemDTO.getItemDescription());
        menuItem.setItemPrice(menuItemDTO.getItemPrice()); // Ensure the DTO and model use BigDecimal
        menuItem.setRestaurant(restaurant);

        menuRepo.save(menuItem);

        return "Menu item details updated successfully";
    }

    public String deleteMenuItem(int restaurantId, int itemId) throws ResourceNotFoundException {
        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        MenuItem menuItem = menuRepo.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));

        if (!menuItem.getRestaurant().getRestaurantId().equals(restaurantId)) {
            throw new ResourceNotFoundException("Menu item does not belong to the specified restaurant");
        }

        menuRepo.delete(menuItem);
        return "Menu item deleted successfully";
    }
}
