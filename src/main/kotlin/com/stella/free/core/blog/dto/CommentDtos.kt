package com.stella.free.core.blog.dto

import com.stella.free.core.account.entity.User
import com.stella.free.core.blog.entity.Comment
import com.stella.free.core.blog.entity.Post


data class CommentSaveDto(
    val userId:Long,
    val content:String,
    val postId: Long,
    val idAncestor:Long?
){

    fun toEntity(post: Post, user: User?): Comment {
        return Comment(
            content = content,
            post = post,
            user = user
        )
    }

}



data class CommentCardDto(
    val id: Long,
    val postId: Long,
    val username:String,
    val content: String,
    val createdAt: String,
)
