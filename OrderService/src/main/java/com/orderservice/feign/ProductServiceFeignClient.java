package com.orderservice.feign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.orderservice.dto.ProductDetails;

@FeignClient(name = "product-service", url = "http://localhost:9091")
public interface ProductServiceFeignClient {

    @GetMapping("/products/get/{id}")
    ProductDetails getProductById(@PathVariable("id") Long id);
}

