package freeapp.life.stella.api.dto

import freeapp.life.stella.api.util.convertToSelfClosing
import freeapp.life.stella.storage.entity.Post
import freeapp.life.stella.storage.entity.User
import freeapp.life.stella.storage.util.toString
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode
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

        // Parse the HTML with Jsoup
        val document =
            Jsoup.parse(this.content)

        // 모든 img 태그와 br 태그를 찾아서 <img>를 <img/>로, <br>을 <br/>로 변환
        for (element in document.select("img, br")) {
            if (element.tagName() == "img" || element.tagName() == "br") {
                convertToSelfClosing(element)
            }
        }


        val cleanedHtml: String = document.body().html().replace("&lt;", "<")
            .replace("&gt;", ">")


        return Post(
            user = user,
            title = this.title,
            content = cleanedHtml,
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
    val contentToMarkdown: String,
    val username: String,
    val thumbnail: String?,
    //val tagNames: String,
    val postTags: List<String>,
    val createdAt: String,
    val deletedAt: LocalDateTime?,
    val comments: List<CommentCardDto>
) {

    companion object {
        fun fromEntity(post: Post, comments: List<CommentCardDto>, postMarkDown: String = ""): PostDetailDto {

            val document =
                Jsoup.parse(post.content)

            // 모든 img 태그와 br 태그를 찾아서 <img>를 <img/>로, <br>을 <br/>로 변환
            for (element in document.select("img, br")) {
                if (element.tagName() == "img" || element.tagName() == "br") {
                    convertToSelfClosing(element)
                }
            }


            val cleanedContent: String = document.body().html().replace("&lt;", "<")
                .replace("&gt;", ">")



            return PostDetailDto(
                id = post.id,
                userId = post.user.id,
                title = post.title,
                thumbnail = post.thumbnail,
                content = cleanedContent,
                postTags = post.postTags.map { it.hashTag.name } ,
                username = post.username,
                createdAt = post.createdAt.toString("YYYY-MM-dd E HH:mm"),
                deletedAt = post.deletedAt,
                comments = comments,
                contentToMarkdown = postMarkDown
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
