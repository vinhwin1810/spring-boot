package com.example.lecture3.products.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.lecture3.products.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    
}
