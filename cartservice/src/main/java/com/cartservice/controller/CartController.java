package com.cartservice.controller;
import com.cartservice.dto.CartItemRequest;
import com.cartservice.feign.UserServiceFeignClient;
import com.cartservice.model.CartItem;
import com.cartservice.service.CartService;
import com.cartservice.dto.UserDTO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserServiceFeignClient userClient;

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody CartItemRequest request, 
                                            @RequestParam String username,
                                            @RequestParam String password) {
        boolean isAuthenticated = userClient.loginUser(new UserDTO(username, password));
        if (isAuthenticated) {
            cartService.addToCart(request,username);
            return ResponseEntity.ok("Product added successfully to cart.");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }

  @DeleteMapping("/delete/{productId}")
    public ResponseEntity<String> deleteFromCart(@PathVariable Long productId, @RequestParam String username,
            @RequestParam String password) {
    	 boolean isAuthenticated = userClient.loginUser(new UserDTO(username, password));
    	if (isAuthenticated) {
            cartService.deleteFromCart(productId);
            return ResponseEntity.ok("Product removed from cart successfully");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }
  
  @PutMapping("/update")
  public ResponseEntity<String> updateCartItem(@RequestParam Long productId,
                                               @RequestParam String username,
                                               @RequestParam String password,
                                               @RequestParam int newQuantity) {
      boolean isAuthenticated = userClient.loginUser(new UserDTO(username, password));
      if (isAuthenticated) {
          cartService.updateCartItem(productId, username, newQuantity);
          return ResponseEntity.ok("Product quantity updated successfully.");
      }
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
  }
  
  @GetMapping("/history")
  public ResponseEntity<?> getCartItems(@RequestParam String username, @RequestParam String password) {
      boolean isAuthenticated = userClient.loginUser(new UserDTO(username, password));
      if (isAuthenticated) {
          List<CartItem> cartItems = cartService.getCartItems(username);
          return ResponseEntity.ok(cartItems);
      }
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
  }
  
  @DeleteMapping("/clear")
  public ResponseEntity<?> clearCart(@RequestParam("username") String username, @RequestParam("password") String password){
	  boolean isAuthenticated = userClient.loginUser(new UserDTO(username, password));
      if (isAuthenticated) {
    	  cartService.clearCart(username);
          return ResponseEntity.ok("Cart cleared successfully");
      }
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
  }
}


