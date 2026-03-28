package com.example.lecture3.config;
import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
public class AppConfig {

    // @Bean
    // public RedisCacheManager cacheManager(RedisConnectionFactory factory) {
    //     RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
    //             .entryTtl(Duration.ofMinutes(3))
    //             .serializeKeysWith(
    //                 RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
    //             .serializeValuesWith(
    //                 RedisSerializationContext.SerializationPair.fromSerializer(
    //                     new Jackson2JsonRedisSerializer<>(Object.class)));

    //     return RedisCacheManager.builder(factory)
    //             .cacheDefaults(config)
    //             .build();
    // }
}

