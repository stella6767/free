package com.stella.free.core.blog.dto

import com.stella.free.core.account.entity.User
import com.stella.free.core.blog.entity.Comment
import com.stella.free.core.blog.entity.Post


data class CommentSaveDto(
    val userId:Long,
    val content:String,
    val postId: Long,
    val idAncestor:Long,
    var paddingLeft:Int,
){
    fun calculatePaddingLeft(){
        if (paddingLeft != 0) this.paddingLeft = this.paddingLeft + 3
    }
    fun toEntity(post: Post, user: User): Comment {
        return Comment(
            content = content,
            post = post,
            user = user
        )
    }

}



data class CommentCardDto(
    val commentId: Long,
    val commentCloserId: Long,
    val depth:Int = 0,
    val idAncestor: Long,
    val idDescendant: Long,
    val parentCommentUsername: String,
    val postId: Long,
    val username:String,
    val content: String,
    val createdAt: String,
    val childComments: MutableList<CommentCardDto> = mutableListOf(),
)


