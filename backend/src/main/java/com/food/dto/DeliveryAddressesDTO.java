package com.food.dto;


import lombok.Data;

@Data
public class DeliveryAddressesDTO {
   private Integer addressId;
   private String addressLine1;
   private String addressLine2;
   private String city;
   private String state;
   private String postalCode;
}

