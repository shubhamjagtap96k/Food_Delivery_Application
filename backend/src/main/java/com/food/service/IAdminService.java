package com.food.service;

import com.food.model.Admin;


public interface IAdminService {
	
	public Admin saveAdmin(Admin admin);
	
	public Admin findByUsernameAndPassword(String username, String password);
	
}
