package com.example.redis.products.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.redis.products.dto.CreateProductRequest;
import com.example.redis.products.dto.PlaceOrderRequest;
import com.example.redis.products.models.Order;
import com.example.redis.products.models.Product;
import com.example.redis.products.service.OrderService;
import com.example.redis.products.service.ProductService;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
// import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor

public class Controller {
    private final ProductService productService;
    private final OrderService orderService;
    @GetMapping("")
    public ResponseEntity<List<Product>> getAllProduct(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping("/create-product")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest request) throws IOException {
        Product newProduct = productService.createProduct(request.getName(), request.getPrice(), request.getStock());
        return ResponseEntity.status(201).body(newProduct);
    }
    @GetMapping("/search")
    public ResponseEntity<Product> getProductByName(@RequestParam String name) {
        try {
            Product product = productService.getProductByName(name);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/order")
    public ResponseEntity<?> placeOrder(@RequestBody PlaceOrderRequest request) throws Exception{
        try {
            Order newOrder = orderService.placeOrder(request.getProductId(), request.getQuantity());
            return ResponseEntity.status(201).body(newOrder);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
