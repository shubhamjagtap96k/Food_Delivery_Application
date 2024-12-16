package com.food.model;



import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "restaurants")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Restaurant {

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Integer restaurantId;

    @Column(name = "restaurant_name")
    private String restaurantName;

    @Column(name = "restaurant_address")
    private String restaurantAddress;

    @Column(name = "restaurant_phone")
    private String restaurantPhone;
    
    @Column(name = "delivery_areas")
    private List<String> deliveryAreas;
    
	@Column(name = "password")
	private String password;
    
    
//    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
//    @JsonManagedReference
//    private List<MenuItem> menuItems;
//
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Order> orders;
//
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Rating> ratings;
    
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<MenuItem> menuItems;

    @ManyToMany(mappedBy = "favoriteRestaurants")
    private Set<Customer> customers = new HashSet<>();
    @Column
    private String image;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return Objects.equals(restaurantId, that.restaurantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantId);
    }

}
