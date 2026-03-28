package com.example.lecture3.products.dto;

import lombok.*;

@Data
public class CreateProductRequest {
    private String name;
    private double price;
    private int stock;
}
