package com.stella.free.core.blog.dto

import com.stella.free.core.blog.entity.Post
import com.stella.free.core.account.entity.User
import com.stella.free.global.util.TimeUtil
import java.time.LocalDateTime


data class PostUpdateDto(
    val id: Long,
    val title: String,
    val content: String,
    val postTags: List<String> = listOf(),
    val username: String,
)




data class PostSaveDto(
    val title: String,
    val content: String,
    val postTags: List<String> = listOf(),
    val username: String = "Anonymous",
) {
    fun toEntity(user: User, thumbnail: String?): Post {

        return Post(
            user = user,
            title = this.title,
            content = this.content,
            thumbnail = thumbnail,
            username = this.username,
        )
    }

}


data class PostDetailDto(
    val id: Long,
    val userId: Long?,
    val title: String,
    val content: String,
    val markDownContent: String,
    val username: String,
    //val userIp:String,
    val thumbnail: String?,
    val tagNames: String,
    val createdAt: String,
    val deletedAt: LocalDateTime?,
) {

    companion object {
        fun fromEntity(post: Post, postMarkDown: String, tagNames:String = ""): PostDetailDto {

            return PostDetailDto(
                id = post.id,
                userId = post.user.id,
                title = post.title,
                thumbnail = post.thumbnail,
                content = post.content,
                tagNames = tagNames,
                markDownContent = postMarkDown,
                username = post.username,
                createdAt = TimeUtil.localDateTimeToString(post.createdAt, "YYYY-MM-dd E HH:mm"),
                deletedAt = post.deletedAt
            )
        }

    }

}


data class PostCardDto(
    val id: Long,
    val title: String,
    val thumbnailContent: String,
    val username: String,
    val thumbnail: String?,
    val createdAt: String,
){

    companion object {

        fun fromEntity(post: Post, thumbnailContent: String): PostCardDto {

            return PostCardDto(
                id = post.id,
                title = post.title,
                thumbnail = post.thumbnail ?: "/img/no-thumbnail.jpeg",
                thumbnailContent = thumbnailContent,
                username = post.username,
                createdAt = TimeUtil.localDateTimeToString(post.createdAt, "YYYY-MM-dd E HH:mm")
            )
        }

    }

}
