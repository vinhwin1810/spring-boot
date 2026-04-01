package com.example.redis.ratelimit.service;
import java.time.Duration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class RateLimitService{
    private final StringRedisTemplate redisTemplate;
    private static final int MAX_REQUESTS = 5;
    private static final Duration WINDOW_DURATION = Duration.ofMinutes(1);
    
    public boolean isRateLimited(String identifier){
        String key = "rate_limit:" + identifier;
        Long count = redisTemplate.opsForValue().increment(key);
        if (count == 1) {
            redisTemplate.expire(key, WINDOW_DURATION);
        }
        return count > MAX_REQUESTS;
    }
}
