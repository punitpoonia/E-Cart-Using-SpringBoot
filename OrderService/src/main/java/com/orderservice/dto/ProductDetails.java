package com.orderservice.dto;

import lombok.Data;

@Data
public class ProductDetails {
	private Long id;
    private String name;
    private double price;
    private String category;
    private String brand;
    private double rating;

}
