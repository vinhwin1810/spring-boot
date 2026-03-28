package com.example.lecture3.products.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lecture3.products.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    public Product findByName(String name);
}
