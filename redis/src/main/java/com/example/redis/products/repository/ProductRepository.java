package com.example.redis.products.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.redis.products.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    public Product findByName(String name);
}
