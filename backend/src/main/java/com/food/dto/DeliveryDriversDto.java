package com.food.dto;
 
 
import java.util.List;
 
import com.food.model.Order;
 
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeliveryDriversDto {
    private Integer driverId;
    private String driverName;
    private String driverPhone;
    private String driverVehicle;
    private String driverLocation;
    private List<Order> orders;
}