
 
package com.food.dao;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import com.food.model.DeliveryDriver;
 
@Repository
public interface IDeliveryDriverRepo extends JpaRepository<DeliveryDriver, Integer> {
    DeliveryDriver findByDriverId(int driverId);
 
}