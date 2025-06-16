package com.cartservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name="NewCart")
@Data
public class CartItem {

   
		
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username; 
    private Long productId;  
    private String productName;
    private double productPrice;
    private int quantity;  
    
    public CartItem() {}
    
    public CartItem(long l, String string, double d, int i) {}
}
