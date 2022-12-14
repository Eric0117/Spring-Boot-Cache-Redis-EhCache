package com.example.demo.config;

import com.example.demo.domain.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheEventListenerConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.event.EventType;
import org.ehcache.jsr107.Eh107Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheConfig.class);

    @Autowired
    RedisConnectionFactory connectionFactory;

    @ConditionalOnProperty(value = "spring.profiles.active", havingValue = "local", matchIfMissing = true)
    @Bean
    public CacheManager ehCacheCacheManager() {
        LOGGER.info("<<<<<<<<<<<<<<<<<<<<<< EhCache Configuration >>>>>>>>>>>>>>>>>>>>>>");
        CacheEventListenerConfigurationBuilder cacheEventListenerConfiguration = CacheEventListenerConfigurationBuilder
                .newEventListenerConfiguration(new CacheEventLogger(), EventType.CREATED, EventType.UPDATED, EventType.EVICTED, EventType.EXPIRED, EventType.REMOVED)
                .ordered().synchronous();

        CacheConfiguration<Long, Product> cacheConfig = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(Long.class,
                        Product.class,
                        ResourcePoolsBuilder.newResourcePoolsBuilder()
                                .offheap(10, MemoryUnit.MB)
                                .build()).withService(cacheEventListenerConfiguration)
                //.withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofSeconds(10))) // ?????? ??? 10?????? ????????? ????????? expire
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(60))) // ?????? ??? 60??? ??? expire
                .build();

        CachingProvider cachingProvider = Caching.getCachingProvider();
        CacheManager cacheManager = cachingProvider.getCacheManager();

        javax.cache.configuration.Configuration<Long, Product> configuration = Eh107Configuration.fromEhcacheCacheConfiguration(cacheConfig);
        cacheManager.createCache("ProductCache", configuration);
        return cacheManager;
    }


    @ConditionalOnProperty(value = "spring.profiles.active", havingValue = "prd", matchIfMissing = false)
    @Bean
    public RedisCacheManager redisCacheManager() {
        LOGGER.info("<<<<<<<<<<<<<<<<<<<<<< Redis Configuration >>>>>>>>>>>>>>>>>>>>>>");
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .disableCachingNullValues()
                .entryTtl(Duration.ofSeconds(60));

        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(connectionFactory)
                .cacheDefaults(redisCacheConfiguration).build();
    }

}
