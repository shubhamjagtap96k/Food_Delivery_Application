package com.food.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.model.Customer;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
//	List<Rating> findByOrderRatingsReview(int a);
	
	Customer findByCustomerName(String customerName);

	//List<Rating> findByOrdersOrderIdRatingReview(Integer customerId);

}
