package com.example.demo.config;

import com.example.demo.domain.Product;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheEventListenerConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.event.EventType;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.time.Duration;

/**
 * @Author Eric
 * @Description https://github.com/HomoEfficio/dev-tips/blob/master/Spring-Boot-Cache-EhCache-Redis.md
 * https://refactorfirst.com/spring-boot-spring-cache-with-ehcache-3
 * @Since 22. 9. 5.
 **/
@EnableCaching
@Configuration
public class CacheConfig {

    @ConditionalOnProperty(value = "spring.profiles.active", havingValue = "local", matchIfMissing = false)
    @Bean
    public CacheManager localBean() {
        CacheEventListenerConfigurationBuilder cacheEventListenerConfiguration = CacheEventListenerConfigurationBuilder
                .newEventListenerConfiguration(new CacheEventLogger(), EventType.CREATED, EventType.UPDATED, EventType.EVICTED, EventType.EXPIRED, EventType.REMOVED)
                .ordered().synchronous();

        CacheConfiguration<Long, Product> cacheConfig = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(Long.class,
                        Product.class,
                        ResourcePoolsBuilder.newResourcePoolsBuilder()
                                .offheap(10, MemoryUnit.MB)
                                .build()).withService(cacheEventListenerConfiguration)
                //.withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofSeconds(10))) // 캐시 후 10초간 요청이 없으면 expire
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(60))) // 캐시 후 60초 후 expire
                .build();

        CachingProvider cachingProvider = Caching.getCachingProvider();
        CacheManager cacheManager = cachingProvider.getCacheManager();

        javax.cache.configuration.Configuration<Long, Product> configuration = Eh107Configuration.fromEhcacheCacheConfiguration(cacheConfig);
        cacheManager.createCache("ProductCache", configuration);
        return cacheManager;
    }
}
