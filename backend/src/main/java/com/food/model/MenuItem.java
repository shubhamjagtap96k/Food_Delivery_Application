package com.food.model;
 
import java.math.BigDecimal;
import java.util.List;
 
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
 
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
 
@Entity
@Table(name = "menuitems")
@Data
public class MenuItem {
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer itemId;
 
    @Column(name = "item_name")
    private String itemName;
 
    @Column(name = "item_description")
    private String itemDescription;
 
    @Column(name = "item_price")
    private BigDecimal itemPrice;
    @Column
    private String image;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @JsonBackReference
    private Restaurant restaurant;
 
    @OneToMany(mappedBy = "menuItem", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderItem> orderItems;
 
}