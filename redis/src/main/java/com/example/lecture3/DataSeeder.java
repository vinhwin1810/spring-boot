package com.example.lecture3;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.lecture3.products.models.Product;
import com.example.lecture3.products.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(String... args) {
        if (productRepository.count() > 0) {
            log.info("Data already seeded, skipping...");
            return;
        }

        productRepository.save(Product.builder().name("iPhone 15").price(999.0).stock(50).build());
        productRepository.save(Product.builder().name("MacBook Pro").price(2499.0).stock(20).build());
        productRepository.save(Product.builder().name("AirPods Pro").price(249.0).stock(100).build());
        productRepository.save(Product.builder().name("iPad Air").price(749.0).stock(30).build());

        log.info("Seeded 4 products.");
    }
}
