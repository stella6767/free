package com.stella.free.core.blog.dto

import com.stella.free.core.blog.entity.Post
import com.stella.free.core.account.entity.User
import java.time.LocalDateTime



data class PostUpdateDto(
    val id:Long,
    val title:String,
    val content:String,
    val postTags: List<String> = listOf(),
    val username:String,
    val password:String,
)

data class PostSaveDto(
    val title:String,
    val content:String,
    val postTags: List<String> = listOf(),
    val username:String,
    val password:String,
){
    fun toEntity(user: User?, thumbnail: String?): Post {

        return Post(
            user = user,
            title = this.title,
            content = this.content,
            thumbnail = thumbnail,
            anonymousUsername = this.username,
            anonymousPassword = this.password
        )
    }

}


data class PostDetailDto(
    val id:Long,
    val userId:Long?,
    val title:String,
    val content:String,
    val markDownContent:String,
    val username:String,
    //val userIp:String,
    val thumbnail:String?,
    val anonymousPassword: String?,
    val tagNames: String,
    val createdAt:String,
    val deletedAt:LocalDateTime?,
)


data class PostCardDto(
    val id:Long,
    val title:String,
    val thumbnailContent:String,
    val username:String,
    val thumbnail:String?,
    val createdAt:String,
)
