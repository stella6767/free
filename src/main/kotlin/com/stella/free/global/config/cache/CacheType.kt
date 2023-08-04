package com.stella.free.global.config.cache

enum class CacheType(
    val cacheName:String,
    val expiredAfterWrite:Long,
    val maximumSize:Long,
    val cacheKey:String,
) {

    //@FieldNameConstants.Include

    PLAYLIST_NAME(CacheName.PLAYLIST_NAME, 100, 10000, "playlistName"),
    ARTIST_NAME(CacheName.ARTIST_NAME, 50, 10000,"artistName"),
    SONG_NAME(CacheName.SONG_NAME, 50, 10000, "songName"),
    GREETING_MESSAGE(CacheName.GREETING_MESSAGE, 50, 10000, "greetingMessage"),
    ALBUM_NAME(CacheName.ALBUM_NAME, 50, 10000, "albumName"),
    ;

    object CacheName {
        const val PLAYLIST_NAME = "playlistName"
        const val SONG_NAME = "songName"
        const val ARTIST_NAME = "artistName"
        const val GREETING_MESSAGE = "greetingMessage"
        const val ALBUM_NAME = "albumName"
    }

}
