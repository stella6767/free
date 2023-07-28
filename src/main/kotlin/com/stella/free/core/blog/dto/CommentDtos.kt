package com.stella.free.core.blog.dto

import com.stella.free.core.account.entity.User
import com.stella.free.core.blog.entity.Comment
import com.stella.free.core.blog.entity.Post


data class CommentSaveDto(
    val userId:Long,
    val content:String,
    val postId: Long,
    val idAncestor:Long
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
    val commentId: Long,
    val commentCloserId: Long = 0,
    val depth:Int = 0,
    val idAncestor: Long = 0,
    val idDescendant: Long = 0,
    val postId: Long,
    val username:String,
    val content: String,
    val createdAt: String,
    val childComments: MutableList<CommentCardDto> = mutableListOf(),
)


data class CommentTestDto(
    val id: Long,
    val childComments: List<CommentTestDto> = listOf(),
    val depth:Int = 0,
    val postId: Long,
    val username:String,
    val content: String,
    val createdAt: String,
)
