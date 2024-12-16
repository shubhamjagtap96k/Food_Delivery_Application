package com.food.service;

import java.util.List;

import com.food.dto.MenuItemDTO;

public interface IMenuItemService {
	
	public List<MenuItemDTO> getAllMenuItems(int restaurantId);
	
	 public String addMenuItem(int restaurantId, MenuItemDTO menuItemDTO);
	 
	 public String updateMenuItem(int restaurantId, int itemId, MenuItemDTO menuItemDTO);
	 
	 public String deleteMenuItem(int restaurantId, int itemId);
}
