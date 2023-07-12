package com.example.securityproject.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class CacheConfig {
    private static final String SESSION = "session";

    private static final Logger LOGGER = LoggerFactory.getLogger("AuditLogger");
    private static final CacheManager manager = new ConcurrentMapCacheManager(SESSION);

    @Bean
    public CacheManager cacheManager() {
        return manager;
    }

    @Scheduled(cron = "0 * * * * *")
    public void autoCleaner() {
        var cache = manager.getCache(SESSION);
        if (cache != null) {
            cache.clear();
            LOGGER.info("Cache session has been cleaned");
        }
    }

    @CacheEvict(allEntries = true, value = SESSION)
    public void cleanSessionCache() {
        var cache = manager.getCache(SESSION);
        if (cache != null) {
            cache.clear();
        }
        LOGGER.info("Session cache has been cleaned");
    }
}
