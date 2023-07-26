package com.stella.free.core.blog.entity

import com.stella.free.core.account.entity.User
import com.stella.free.core.blog.dto.CommentCardDto
import com.stella.free.global.entity.BaseEntity
import com.stella.free.global.util.TimeUtil
import jakarta.persistence.*
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Entity
@Table(name = "comment")
class Comment(
    id: Long = 0,
    content: String,
    post: Post,
    user: User?,
) : BaseEntity() {

    @Column(name = "content", nullable = false, length = 1000)
    var content: String = content
        protected set

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Post::class)
    var post: Post = post
        protected set

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User::class)
    var user = user
        protected set



    fun toCardDto(): CommentCardDto {

        return CommentCardDto(
            id = this.id,
            postId = this.post.id,
            content = this.content,
            username = this.user?.username ?: "익명",
            createdAt = TimeUtil.localDateTimeToString(this.createdAt, "hh:mm:ss a")
        )
    }


}