package com.orderservice.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // Custom queries can be added here if needed (e.g., find by username, status, etc.)
	List<Order> findByUsername(String username);
	
	
}