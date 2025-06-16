package com.orderservice.service;

import com.cartservice.model.CartItem;
import com.orderservice.dto.OrderDTO;
import com.orderservice.dto.ProductDetails;
import com.orderservice.feign.CartServiceFeignClient;
import com.orderservice.feign.ProductServiceFeignClient;
import com.orderservice.model.Order;
import com.orderservice.repo.OrderRepository;

import feign.FeignException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartServiceFeignClient cartServiceFeignClient;

    @Autowired
    private ProductServiceFeignClient productServiceFeignClient;

    public Order placeOrder(String username, String password, String shippingAddress) {
        List<CartItem> cartItems = cartServiceFeignClient.getCartItems(username, password);

        if (cartItems.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The cart is empty. Please add items to the cart before placing an order.");
        }

        double totalAmount = cartItems.stream()
                .mapToDouble(CartItem::getProductPrice)
                .sum();

        Order order = new Order();
        order.setUsername(username);
        order.setProductIds(cartItems.stream().map(CartItem::getProductId).toList());
        order.setTotalAmount(totalAmount);
        order.setOrderStatus("PLACED");
        order.setDeliveryStatus("IN_TRANSIT");
        order.setShippingAddress(shippingAddress);

        // Clear cart after placing the order
        cartServiceFeignClient.clearCart(username, password);

        return orderRepository.save(order);
    }

    public Order getOrderStatus(String username, String password, Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }

    public Order updateOrderStatus(Long orderId, String newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        order.setOrderStatus(newStatus);
        return orderRepository.save(order);
    }

    public Order updateDeliveryStatus(Long orderId, String deliveryStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        order.setDeliveryStatus(deliveryStatus);
        return orderRepository.save(order);
    }

    public List<OrderDTO> getOrderHistoryWithDetails(String username) {
        // Fetch orders from the repository
        List<Order> orders = orderRepository.findByUsername(username);

        if (orders.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No orders found for the username: " + username);
        }

        // Convert Order entities to OrderDTOs with product details
        return orders.stream().map(order -> {
            List<ProductDetails> productDetails = order.getProductIds().stream()
                    .map(productId -> {
                        try {
                            return productServiceFeignClient.getProductById(productId);
                        } catch (FeignException.NotFound e) {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with ID " + productId + " not found");
                        } catch (FeignException e) {
                            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching product details for ID " + productId, e);
                        }
                    })
                    .collect(Collectors.toList());

            return new OrderDTO(
                    order.getId(),
                    order.getUsername(),
                    productDetails,
                    order.getTotalAmount(),
                    order.getOrderStatus(),
                    order.getDeliveryStatus(),
                    order.getShippingAddress()
            );
        }).collect(Collectors.toList());
    }
}
