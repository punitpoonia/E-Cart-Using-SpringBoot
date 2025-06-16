package com.orderservice.model;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity(name="ordersTable")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;  
    @ElementCollection
    private List<Long> productIds;  
    private double totalAmount;  
    private String orderStatus;  
    private String deliveryStatus; 
    private String shippingAddress; 

}