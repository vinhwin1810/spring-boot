package com.example.redis.config;
import org.springframework.context.annotation.Configuration;

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

