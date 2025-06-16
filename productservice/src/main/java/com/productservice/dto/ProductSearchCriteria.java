package com.productservice.dto;

import lombok.Data;

@Data
public class ProductSearchCriteria {
    private String name;
    private String category;
    private String brand;
    private Double minPrice;
    private Double maxPrice;
    public ProductSearchCriteria() {}
    public ProductSearchCriteria(String name, String category, String brand, Double minPrice, Double maxPrice) {
        this.name = name;
        this.category = category;
        this.brand = brand;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }
}
