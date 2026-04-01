package com.example.redis.products.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.redis.products.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    
}
