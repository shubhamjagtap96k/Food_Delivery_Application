package com.food.model;


import java.math.BigDecimal;

import java.time.LocalDate;
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
@Table(name = "coupons")
@Data
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer couponId;

    @Column(name = "coupon_code", nullable = false, unique = true)
    private String couponCode;

    @Column(name = "discount_amount", nullable = false)
    private BigDecimal discountAmount;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

//    @OneToMany(mappedBy = "coupon", cascade = CascadeType.ALL)
//    @JsonManagedReference
//    private List<OrderCoupon> orderCoupons;
    
}

