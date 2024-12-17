// AdminService.java
package com.food.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.dao.AdminRepo;
import com.food.model.Admin;

@Service
public class AdminService implements IAdminService {

    @Autowired
    private AdminRepo adminRepository;

    public Admin saveAdmin(Admin admin) {
        return adminRepository.save(admin);
    }
    
    public Admin findByUsernameAndPassword(String username, String password) {
        return adminRepository.findByUsernameAndPassword(username, password);
    }
}
