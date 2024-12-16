package com.food.model;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "deliverydrivers")
@Data
public class DeliveryDriver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer driverId;

    @Column(name = "driver_name", nullable = false)
    private String driverName;

    @Column(name = "driver_phone", nullable = false, unique = true)
    private String driverPhone;

    @Column(name = "driver_vehicle", nullable = false)
    private String driverVehicle;
    
    @Column(name = "driver_location")
	private String driverLocation;

    @OneToMany(mappedBy = "deliveryDriver", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Order> orders;
    
}
