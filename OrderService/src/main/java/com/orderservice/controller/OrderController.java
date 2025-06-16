package com.orderservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cartservice.dto.UserDTO;
import com.orderservice.dto.OrderDTO;
import com.orderservice.feign.UserServiceFeignClient;
import com.orderservice.model.Order;
import com.orderservice.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    
    @Autowired 
    private UserServiceFeignClient userClient;

    @PostMapping("/place")
    public ResponseEntity<String> placeOrder(@RequestParam String username, @RequestParam String password,@RequestParam String shippingAddress) {
    	 boolean isAuthenticated = userClient.loginUser(new UserDTO(username, password));
         if (isAuthenticated) {
    	   System.out.println("Controller user name :"+username);
           orderService.placeOrder(username, password,shippingAddress);
         
        return ResponseEntity.ok("Order Placed Successfully");
         }
         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username and password");
    }

    @GetMapping("/{orderId}/status")
    public ResponseEntity<String> getOrderStatus(@RequestParam String username, @RequestParam String password,@PathVariable Long orderId) {
    	boolean isAuthenticated = userClient.loginUser(new UserDTO(username, password));
    	if (isAuthenticated) {
      	   System.out.println("Controller user name :"+username);
          orderService.getOrderStatus(username, password,orderId);
           
          return ResponseEntity.ok("Order Placed Successfully");
           }
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username and password");
        
    }
    @GetMapping("/history")
    public ResponseEntity<List<OrderDTO>> getOrderHistory(@RequestParam("username") String username,@RequestParam("password") String password) {
    	boolean isAuthenticated = userClient.loginUser(new UserDTO(username, password));
    	if (isAuthenticated) {
    		List<OrderDTO> orderHistory = orderService.getOrderHistoryWithDetails(username);
            return ResponseEntity.ok(orderHistory);
           }
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    	
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId, @RequestParam String newStatus) {
        Order updatedOrder = orderService.updateOrderStatus(orderId, newStatus);
        return ResponseEntity.ok(updatedOrder);
    }
    @PutMapping("/{orderId}/delivery-status")
    public ResponseEntity<Order> updateDeliveryStatus(@PathVariable Long orderId, @RequestParam String deliveryStatus) {
        Order updatedOrder = orderService.updateDeliveryStatus(orderId, deliveryStatus);
        return ResponseEntity.ok(updatedOrder);
    }
}