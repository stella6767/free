package com.stella.free.dto

import com.stella.free.entity.Post
import com.stella.free.entity.User
import java.time.LocalDateTime


data class PostSaveDto(
    val title:String,
    val content:String,
    //val thumbnail:String,
    val anonymousUsername:String = "",
){
    fun toEntity(user:User?): Post {

        return Post(
            user = user,
            title = this.title,
            content = this.content,
            thumbnail = "",
            anonymousUsername = this.anonymousUsername
        )
    }

}


data class PostDetailDto(
    val id:Long,
    val title:String,
    val content:String,
    val username:String,
    //val userIp:String,
    val createdAt:LocalDateTime,
)
