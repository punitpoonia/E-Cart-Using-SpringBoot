package com.orderservice.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
@Data
public class OrderDTO {
    private Long id;
    private String username;
    private List<ProductDetails> productDetails; // Replaces productIds
    private double totalAmount;
    private String orderStatus;
    private String deliveryStatus;
    private String shippingAddress;
   

    public OrderDTO(Long id, String username, List<ProductDetails> productDetails,
                                double totalAmount, String orderStatus, String deliveryStatus,
                                String shippingAddress) {
        this.id = id;
        this.username = username;
        this.productDetails = productDetails;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.deliveryStatus = deliveryStatus;
        this.shippingAddress = shippingAddress;
       
    }

    // Getters and Setters
}


