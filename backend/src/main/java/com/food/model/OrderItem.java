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
@Table(name = "orderitems")
@Data
public class OrderItem {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderItemId;
 
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonBackReference
    private Order order;
 
    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    @JsonBackReference
    private MenuItem menuItem;
 
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    
    @Column(name = "price")
    private Integer price;
 
}