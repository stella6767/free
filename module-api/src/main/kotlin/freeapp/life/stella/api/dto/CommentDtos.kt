package freeapp.life.stella.api.dto

import freeapp.life.stella.storage.entity.User
import freeapp.life.stella.storage.entity.Comment
import freeapp.life.stella.storage.entity.CommentClosure
import freeapp.life.stella.storage.entity.Post
import freeapp.life.stella.storage.util.toString


data class CommentSaveDto(
    val userId:Long?,
    val nickName: String,
    val password:String?,
    val content:String,
    val postId: Long,
    val idAncestor:Long,
    var paddingLeft:Int,
){
    fun calculatePaddingLeft(){
        if (paddingLeft != 0) this.paddingLeft = this.paddingLeft + 3
    }
    fun toEntity(post: Post, user: User?): Comment {

        return Comment(
            nickName = nickName,
            password = password,
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
    val userId: Long,
    val username:String,
    val content: String,
    val createdAt: String,
    val childComments: MutableList<CommentCardDto> = mutableListOf(),
) {
    companion object {
        fun fromEntity(commentClosure: CommentClosure): CommentCardDto {

            val parentCommentUsername =
                if (commentClosure.idAncestor != commentClosure.idDescendant) commentClosure.idAncestor.nickName  else ""

            return CommentCardDto(
                commentId = commentClosure.idDescendant.id,
                commentCloserId = commentClosure.id,
                depth = commentClosure.depth,
                idAncestor = commentClosure.idAncestor.id,
                parentCommentUsername = parentCommentUsername,
                idDescendant = commentClosure.idDescendant.id,
                postId = commentClosure.idDescendant.post.id,
                userId = commentClosure.idDescendant.user?.id ?: 0L,
                content = commentClosure.idDescendant.content,
                username = commentClosure.idDescendant.nickName,
                createdAt = commentClosure.idDescendant.createdAt.toString("hh:mm:ss a")
            )

        }
    }

}


