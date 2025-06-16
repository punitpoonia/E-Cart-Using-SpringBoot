package com.productservice;
import com.productservice.controller.ProductController;
import com.productservice.model.Product;
import com.productservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductserviceApplicationTests {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addProduct() {
        Product product = new Product(1L, "Laptop", "Electronics", "Dell", 1000.0);
        when(productService.addProduct(any(Product.class))).thenReturn(product);
        ResponseEntity<Product> response = productController.addProduct(product);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(product, response.getBody());
        verify(productService, times(1)).addProduct(product);
    }
    @Test
    void updateProduct() {
        Product existingProduct = new Product(1L, "Laptop", "Electronics", "Dell", 1000.0);
        Product updatedProduct = new Product(1L, "Laptop", "Electronics", "Dell", 1200.0);
        when(productService.updateProduct(eq(1L), any(Product.class))).thenReturn(updatedProduct);
        ResponseEntity<Product> response = productController.updateProduct(1L, updatedProduct);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedProduct, response.getBody());
        verify(productService, times(1)).updateProduct(1L, updatedProduct);
    }

    @Test
    void deleteProduct() {
        when(productService.deleteProduct(1L)).thenReturn(true);
        ResponseEntity<String> response = productController.deleteProduct(1L);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Product deleted successfully", response.getBody());
        verify(productService, times(1)).deleteProduct(1L);
    }

   
}
