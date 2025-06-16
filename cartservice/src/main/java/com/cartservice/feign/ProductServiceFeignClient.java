package com.cartservice.feign;

import com.productservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "productservice", url = "http://localhost:9091/products")
public interface ProductServiceFeignClient {

    @GetMapping("/get/{id}")
    Product getProductById(@PathVariable("id") Long id);
}
