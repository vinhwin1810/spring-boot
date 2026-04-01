package com.example.redis.ratelimit.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/api")
public class RateLimitController {
    @GetMapping("/inspect")
    public ResponseEntity<String> inspect(@RequestHeader("X-API-KEY") String apiKey){
        return ResponseEntity.status(200).body("ALL GOOD! PASSED API KEY: " + apiKey);
    }
    
}
