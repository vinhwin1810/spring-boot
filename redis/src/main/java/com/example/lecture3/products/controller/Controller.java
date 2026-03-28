package com.example.lecture3.products.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.lecture3.products.service.ProductService;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.lecture3.products.dto.CreateProductRequest;
import com.example.lecture3.products.models.Product;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
// import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor

public class Controller {
    private final ProductService productService;
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
}
