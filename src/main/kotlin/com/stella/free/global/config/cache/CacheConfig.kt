package com.stella.free.global.config.cache

import com.github.benmanes.caffeine.cache.Caffeine
import com.stella.free.global.util.logger
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCache
import org.springframework.cache.support.SimpleCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors

@Configuration
@EnableCaching
class CacheConfig(

) {
    private val log = logger()

    @Bean
    fun cacheManager(): CacheManager {
        val cacheManager = SimpleCacheManager()
        val caches: List<CaffeineCache> = Arrays.stream(CacheType.values())
            .map { cache ->
                makeCaffeineCache(cache)
            }
            .collect(Collectors.toList())
        cacheManager.setCaches(caches)
        return cacheManager
    }


    private fun makeCaffeineCache(cache: CacheType) = CaffeineCache(
        cache.cacheName, Caffeine.newBuilder().recordStats()
            .expireAfterWrite(cache.expiredAfterWrite, TimeUnit.SECONDS)
            .maximumSize(cache.maximumSize)
            .removalListener { key: Any?, value: Any?, cause ->
                log.info("Key {} was evicted ({}): {}", key, cause, value)
            }
            .build()
    )




}