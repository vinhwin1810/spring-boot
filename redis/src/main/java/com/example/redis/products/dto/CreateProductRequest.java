package com.example.redis.products.dto;

import lombok.*;

@Data
public class CreateProductRequest {
    private String name;
    private double price;
    private int stock;
}
