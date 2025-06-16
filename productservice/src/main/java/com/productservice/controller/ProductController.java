package com.productservice.controller;

import com.productservice.dto.ProductSearchCriteria;
import com.productservice.exception.ResourceNotFoundException;
import com.productservice.model.Product;
import com.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.addProduct(product));
    }
    
    

    @PutMapping("/update")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(product);
        if (updatedProduct == null) {
            throw new ResourceNotFoundException("Product with ID " +  " not found");
        }
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        boolean isDeleted = productService.deleteProduct(id);
        if (!isDeleted) {
            throw new ResourceNotFoundException("Product with ID " + id + " not found");
        }
        return ResponseEntity.ok("Product deleted successfully");
    }

    @GetMapping("/get/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        Product product = productService.getProduct(id);
        if (product == null) {
            throw new ResourceNotFoundException("Product with ID " + id + " not found");
        }
        return ResponseEntity.ok(product);
    }
    
    @GetMapping("/getProduct")
	public List<Product> fetchProductList(){ 
		List<Product> users=new ArrayList<>();
		users =productService.getAllProducts();
		return users;
	}

    @PostMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestBody ProductSearchCriteria criteria) {
        List<Product> products = productService.searchProducts(
                criteria.getName(),
                criteria.getCategory(),
                criteria.getBrand(),
                criteria.getMinPrice(),
                criteria.getMaxPrice()
        );
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No products found for the given criteria");
        }
        return ResponseEntity.ok(products);
    }
}
