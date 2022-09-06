package com.example.demo.config;


import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.cache.event.CacheEntryEventFilter;

/**
 * @Author Eric
 * @Description
 * @Since 22. 9. 6.
 **/
public class CacheEventLogger implements CacheEventListener<Object, Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheEventLogger.class);

    @Override
    public void onEvent(
            CacheEvent<? extends Object, ? extends Object> cacheEvent) {
        LOGGER.info("================================================[Cache Event]================================================");
        LOGGER.info("===== [Cache Event] Product Id : " + cacheEvent.getKey());
        LOGGER.info("===== [Cache Event] Event Type : " + cacheEvent.getType());
        LOGGER.info("===== [Cache Event] Old Value : " + cacheEvent.getOldValue());
        LOGGER.info("===== [Cache Event] New Value : " + cacheEvent.getNewValue());
        LOGGER.info("=============================================================================================================");
    }
}
