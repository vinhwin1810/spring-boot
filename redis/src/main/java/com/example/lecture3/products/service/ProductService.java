package com.example.lecture3.products.service;


import java.time.Duration;
import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.lecture3.products.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.example.lecture3.products.models.Product;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private static final String REDIS_KEY = "products";
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    private static final  int REDIS_CACHE_TTL_MINUTES = 3;

    public Product createProduct(String name, Double price, Integer stock) {
        Product product = Product.builder()
                            .name(name)
                            .price(price)
                            .stock(stock)
                            .build();
        productRepository.save(product);
        String key = REDIS_KEY + ":" + name;
        try {
            String productJson = objectMapper.writeValueAsString(product);
            redisTemplate.opsForValue().set(key, productJson, Duration.ofMinutes(REDIS_CACHE_TTL_MINUTES));
        } catch (JacksonException e) {
            log.error("Error caching product: {}", e.getMessage());
            throw new RuntimeException("Error caching product: " + e.getMessage());
        }
        return product;

    }
    public Product getProductByName(String name) {
        String cacheKey = REDIS_KEY + ":" + name;
        String cacheData = redisTemplate.opsForValue().get(cacheKey);

        if(cacheData != null) {
            log.info("Cache hit for product: {}", name);
            return objectMapper.readValue(cacheData, Product.class);
        }
        else{
            log.info("Cache miss for product: {}", name);
            Product product = productRepository.findByName(name);
            if(product != null) {
                redisTemplate.opsForValue().set(cacheKey, objectMapper.writeValueAsString(product), Duration.ofMinutes(REDIS_CACHE_TTL_MINUTES));
                return product;
            }
            else{
                throw new RuntimeException("Product not found: " + name);
            }
        }
    }
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
