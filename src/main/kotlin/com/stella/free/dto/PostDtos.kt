package com.stella.free.dto

import com.stella.free.entity.Post
import com.stella.free.entity.User


data class PostSaveDto(
    val title:String,
    val content:String,
    val thumbnail:String,
){
    fun toEntity(user:User?): Post {

        return Post(
            user = user,
            title = this.title,
            content = this.content,
            thumbnail = this.thumbnail
        )
    }

}
