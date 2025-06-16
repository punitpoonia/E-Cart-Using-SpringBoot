package com.cartservice;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.cartservice.dto.CartItemRequest;
import com.cartservice.feign.ProductServiceFeignClient;
import com.cartservice.feign.UserServiceFeignClient;
import com.cartservice.service.CartService;
import com.productservice.model.Product;
import com.cartservice.controller.CartController;
import com.cartservice.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CartserviceApplicationTests {

    @InjectMocks
    private CartController cartController;

    @Mock
    private CartService cartService;

    @Mock
    private UserServiceFeignClient userServiceFeignClient;

    @Mock
    private ProductServiceFeignClient productServiceFeignClient;

    private CartItemRequest cartItemRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cartItemRequest = new CartItemRequest(1L, 2);
    }

    @Test
    void addToCart() {
        String username = "testUser";
        String password = "testPassword";
        when(userServiceFeignClient.loginUser(new UserDTO(username, password))).thenReturn(true);
        when(productServiceFeignClient.getProductById(cartItemRequest.getProductId())).thenReturn(new Product());
        ResponseEntity<String> response = cartController.addToCart(cartItemRequest, username, password);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Product added successfully to cart.", response.getBody());
        verify(cartService, times(1)).addToCart(cartItemRequest, username);
    }
    
    @Test
    void deleteFromCart() {
        Long productId = 1L;
        String username = "testUser";
        String password = "testPassword";
        when(userServiceFeignClient.loginUser(new UserDTO(username, password))).thenReturn(true);

        ResponseEntity<String> response = cartController.deleteFromCart(productId, username, password);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Product removed from cart successfully", response.getBody());
        verify(cartService, times(1)).deleteFromCart(productId);
    }
    
    @Test
    void updateCartItem() {
       
        Long productId = 1L;
        String username = "testUser";
        String password = "testPassword";
        int newQuantity = 3;
        when(userServiceFeignClient.loginUser(new UserDTO(username, password))).thenReturn(true);

        ResponseEntity<String> response = cartController.updateCartItem(productId, username, password, newQuantity);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Product quantity updated successfully.", response.getBody());
        verify(cartService, times(1)).updateCartItem(productId, username, newQuantity);
    }


    
}
