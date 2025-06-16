package com.cartservice.service;
import com.cartservice.dto.CartItemRequest;
import com.cartservice.exception.CartNotFoundException;
import com.cartservice.exception.CartOperationException;
import com.cartservice.exception.ProductNotFoundException;
import com.cartservice.feign.ProductServiceFeignClient;
import com.cartservice.model.CartItem;
import com.cartservice.repository.CartRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ProductServiceFeignClient productServiceFeignClient;

	public void addToCart(CartItemRequest request, String username) {
		  var product = productServiceFeignClient.getProductById(request.getProductId());
		  
	        if (cartRepository.findByUsernameAndProductId(username, request.getProductId()).isPresent()) {
	            throw new RuntimeException("Item already in cart. Try updating the quantity.");
	        }
	        CartItem cartItem = new CartItem();
	        cartItem.setUsername(username);
	        cartItem.setProductId(request.getProductId());
	        cartItem.setProductName(product.getName());
	        double totalPrice = product.getPrice() * request.getQuantity();
	        cartItem.setProductPrice(totalPrice);
	        cartItem.setQuantity(request.getQuantity());
	        cartRepository.save(cartItem);
	    }
	    
	    
	public void updateCartItem(Long productId, String username, int newQuantity) {
	    CartItem cartItem = cartRepository.findByUsernameAndProductId(username, productId)
	            .orElseThrow(() -> new RuntimeException("Item not found in cart."));

	    int oldQuantity = cartItem.getQuantity();

	    if (oldQuantity > 0) {
	       
	        double unitPrice = cartItem.getProductPrice() / oldQuantity;
	        double updatedPrice = unitPrice * newQuantity;

	        cartItem.setQuantity(newQuantity);
	        cartItem.setProductPrice(updatedPrice);
	        cartRepository.save(cartItem);
	    } else {
	        throw new IllegalArgumentException("Current quantity cannot be zero. You can delete it");
	    }
	}

	 
	 
	 
	    public void deleteFromCart(Long productId) {
	    	if (!cartRepository.existsById(productId)) {
	            throw new ProductNotFoundException("Product with ID " + productId + " not found in the cart.");
	        }
	        cartRepository.deleteById(productId);
	    }
	    
	    public List<CartItem> getCartItems(String username) {
	        List<CartItem> cartItems = cartRepository.findByUsername(username);
	        if (cartItems == null || cartItems.isEmpty()) {
	            throw new CartNotFoundException("No cart items found for username: " + username);
	        }
	        return cartItems;
	    }

	    public void clearCart(String username) {
	        List<CartItem> userCartItems = cartRepository.findByUsername(username);
	        if (userCartItems == null || userCartItems.isEmpty()) {
	            throw new CartNotFoundException("No cart items found for username: " + username);
	        }
	        try {
	            cartRepository.deleteAll(userCartItems);
	        } catch (Exception e) {
	            throw new CartOperationException("An error occurred while clearing the cart for username: " + username, e);
	        }
	    }

}