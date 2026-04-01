package com.example.redis.products.service;

import org.springframework.stereotype.Service;

import com.example.redis.products.models.Order;
import com.example.redis.products.models.OrderStatus;
import com.example.redis.products.models.Product;
import com.example.redis.products.repository.OrderRepository;
import com.example.redis.products.repository.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.data.redis.core.RedisTemplate;
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private static final String REDIS_KEY = "Order::";


    private boolean tryLock(String lockKey, String lockValue, Duration waitTime, Duration retryInterval) throws InterruptedException {
        LocalDateTime deadline = LocalDateTime.now().plus(waitTime);

        while (LocalDateTime.now().isBefore(deadline)) {
            Boolean acquired = redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, Duration.ofSeconds(10));
            if (Boolean.TRUE.equals(acquired)) {
                return true;
            }
            Thread.sleep(retryInterval.toMillis());
        }

        return false;
    }
    @Transactional
    public Order executeOrder(Long productId, int quantity) throws InterruptedException {
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new RuntimeException("No product found"));

    if (quantity <= 0) {
        throw new IllegalArgumentException("Quantity must be > 0");
    }
    if (product.getStock() < quantity){
        throw new RuntimeException("BRO, TOO MUCH ORDERED");
    }
    int newQuantity = product.getStock() - quantity;
    product.setStock(newQuantity);

    Order newOrder = Order.builder()
        .product(product)
        .quantity(quantity)
        .createdAt(LocalDateTime.now())
        .status(OrderStatus.SUCCESS)
        .build();
    
    return orderRepository.save(newOrder);
}

    public Order placeOrder(Long productId, int quantity) throws InterruptedException {
    String fullKey = REDIS_KEY + productId;
    Boolean canPlace = tryLock(fullKey, "IT'S LOCKED BRO, WAIT", Duration.ofSeconds(10), Duration.ofSeconds(2));
    if(Boolean.FALSE.equals(canPlace)){
        log.info("BRO, Either TIMEOUT OR CANNOT BRUH");
        throw new RuntimeException("TRY AGAIN LATER BRUH");
    }
    try{ 
        Order o = executeOrder(productId, quantity);
        return o;
    }
    finally{
        redisTemplate.opsForValue().getAndDelete(fullKey);
    }
}

}
