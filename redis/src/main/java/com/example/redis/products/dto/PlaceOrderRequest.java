package com.example.redis.products.dto;

import lombok.Data;

@Data
public class PlaceOrderRequest {
    private Long productId;
    private int quantity;
}
