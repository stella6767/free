package freeapp.life.stella.api.dto

import freeapp.life.stella.storage.entity.Post
import freeapp.life.stella.storage.entity.User
import freeapp.life.stella.storage.util.toString
import org.springframework.util.StringUtils
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
    val username: String,
) {
    fun toEntity(user: User, thumbnail: String?): Post {

        return Post(
            user = user,
            title = this.title,
            content = this.content,
            thumbnail = thumbnail,
            username = if (StringUtils.hasLength(this.username)) this.username else "Anonymous",
        )
    }

}


data class PostDetailDto(
    val id: Long,
    val userId: Long?,
    val title: String,
    val content: String,
    val username: String,
    val thumbnail: String?,
    //val tagNames: String,
    val postTags: List<String>,
    val createdAt: String,
    val deletedAt: LocalDateTime?,
) {

    companion object {
        fun fromEntity(post: Post): PostDetailDto {

            return PostDetailDto(
                id = post.id,
                userId = post.user.id,
                title = post.title,
                thumbnail = post.thumbnail,
                content = post.content,
                postTags = post.postTags.map { it.hashTag.name } ,
                username = post.username,
                createdAt = post.createdAt.toString("YYYY-MM-dd E HH:mm"),
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
) {

    companion object {

        fun fromEntity(post: Post, thumbnailContent: String): PostCardDto {

            return PostCardDto(
                id = post.id,
                title = post.title,
                thumbnail = post.thumbnail ?: "/img/no-thumbnail.jpeg",
                thumbnailContent = thumbnailContent,
                username = post.username,
                createdAt = post.createdAt.toString("YYYY-MM-dd E HH:mm")
            )
        }


    }

}
