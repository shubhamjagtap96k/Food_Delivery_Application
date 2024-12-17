package com.food.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.dto.MenuItemDTO;
import com.food.exception.ResourceNotFoundException;
import com.food.service.IMenuItemService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/restaurants/{restaurantId}/menu")
public class MenuItemsController {
	
	@Autowired
	IMenuItemService menuService;
	
	@GetMapping
	public List<MenuItemDTO> getAllMenuItems(@PathVariable int restaurantId) throws ResourceNotFoundException {
		return menuService.getAllMenuItems(restaurantId);
	}
	
	@PostMapping
	public String addMenuItem(@PathVariable int restaurantId, @RequestBody MenuItemDTO menuItemDTO) {
		return menuService.addMenuItem(restaurantId, menuItemDTO);
	}
	
	 @PutMapping("/{itemId}")
	 public String updateMenuItem(@PathVariable int restaurantId, @PathVariable int itemId, @RequestBody MenuItemDTO menuItemDTO) throws ResourceNotFoundException {
	    return menuService.updateMenuItem(restaurantId, itemId, menuItemDTO);
	 }
	 
	 @DeleteMapping("/{itemId}")
	 public String deleteMenuItem(@PathVariable int restaurantId, @PathVariable int itemId) throws ResourceNotFoundException {
	     return menuService.deleteMenuItem(restaurantId, itemId);
	     
	 }
}
