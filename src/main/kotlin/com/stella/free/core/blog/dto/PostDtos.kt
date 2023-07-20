package com.stella.free.core.blog.dto

import com.stella.free.core.blog.entity.Post
import com.stella.free.core.account.entity.User
import java.time.LocalDateTime


data class PostSaveDto(
    val title:String,
    val content:String,
    //val thumbnail:String,
    val anonymousUsername:String = "",
){
    fun toEntity(user: User?): Post {

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