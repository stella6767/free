package com.stella.free.global.config.cache

enum class CacheType(
    val cacheName:String,
    val expiredAfterWrite:Long,
    val maximumSize:Long,
    val cacheKey:String,
) {

    PLAYLIST_NAME(CacheName.PLAYLIST_NAME, 100, 10000, "playlistName"),

    ;

    object CacheName {
        const val PLAYLIST_NAME = "playlistName"
    }

}
