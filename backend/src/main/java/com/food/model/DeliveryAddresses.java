package com.food.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
 
@Entity
@Table(name = "deliveryaddresses")
@Data
public class DeliveryAddresses {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addressId;
 
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "customer_id")
    private Customer customers;
 
    @Column(name = "address_line1")
    private String addressLine1;
 
    @Column(name = "address_line2")
    private String addressLine2;
 
    @Column(name = "city")
    private String city;
 
    @Column(name = "state")
    private String state;
 
    @Column(name = "postal_code")
    private String postalCode;
 
    
    //    @ManyToOne
//    @JoinColumn(name = "customer_id")
//	@JsonBackReference
//	private Customer customer;
//   
}