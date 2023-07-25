package com.stella.free.core.blog.dto

import com.stella.free.core.account.entity.User
import com.stella.free.core.blog.entity.Comment
import com.stella.free.core.blog.entity.Post


data class CommentSaveDto(
    val userId:Long?,
    val anonymousUsername:String,
    val anonymousPassword:String,
    val content:String,
    val postId: Long,
    val idAncestor:Long?
){

    fun toEntity(post: Post, user: User?): Comment {
        return Comment(
            content = content,
            post = post,
            anonymousUsername = anonymousUsername,
            anonymousPassword = anonymousPassword,
            user = user
        )
    }

}



data class CommentRes(
    val content: String,
)
