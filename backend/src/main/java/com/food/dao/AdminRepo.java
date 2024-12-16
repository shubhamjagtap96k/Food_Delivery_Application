package com.food.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.food.model.Admin;

@Repository
public interface AdminRepo extends JpaRepository<Admin, Integer> {
	
	Admin findByUsernameAndPassword(String username, String password);
}
