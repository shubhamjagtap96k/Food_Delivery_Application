package com.food.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "customers")
public class Customer {
	 @Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	@JsonIgnore
	private Integer customerId;
 
	@Column(length = 255)
	private String customerName;
 
	@Column(length = 255)
	private String customerEmail;
 
	@Column(length = 20)
	private String customerPhone;
	
	@Column
	private String password;
 
	@OneToMany(mappedBy = "customers", cascade = CascadeType.ALL)
	@JsonManagedReference
	
	private List<DeliveryAddresses> deliveryAddresses;
 
	@OneToMany(mappedBy = "customers", cascade = CascadeType.ALL)
	
	private List<Order> orders;
	
	
    @OneToMany(mappedBy = "customers", cascade = CascadeType.ALL)
    
    private List<Rating> ratings;
 
	@ManyToMany(fetch = FetchType.EAGER)
	
    @JoinTable(
        name = "customer_favorite_restaurants",
        joinColumns = @JoinColumn(name = "customer_id"),
        inverseJoinColumns = @JoinColumn(name = "restaurant_id")
    )
    private Set<Restaurant> favoriteRestaurants = new HashSet<>();
 
 
}