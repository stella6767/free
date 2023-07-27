package com.stella.free.core.blog.entity

import com.stella.free.core.account.entity.User
import com.stella.free.core.blog.dto.PostCardDto
import com.stella.free.core.blog.dto.PostDetailDto

import com.stella.free.global.entity.BaseEntity
import com.stella.free.global.util.TimeUtil
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault

@Entity
@Table(name = "post")
class Post(
    id:Long = 0,
    title: String,
    content:String,
    thumbnail:String?,
    user: User?,
    anonymousUsername:String,
) : BaseEntity(id=id) {

    @Column(nullable = false, length = 100)
    val title = title

    @Column(nullable = false, length = 100000)
    val content = content

    @Column(nullable = true, length = 1000)
    var thumbnail = thumbnail

    @ColumnDefault("0")
    var count = 0

    @Column(name = "anonymous_username")
    val anonymousUsername = anonymousUsername

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    val user = user


    fun updateId(id:Long){
        this.id = id
    }


    fun toCardDto(thumbnailContent: String): PostCardDto {

        return PostCardDto(
            id = this.id,
            title = this.title,
            thumbnail = this.thumbnail ?: "/img/no-thumbnail.jpeg",
            thumbnailContent = thumbnailContent,
            username = this.user?.username ?: this.anonymousUsername,
            createdAt = TimeUtil.localDateTimeToString(this.createdAt, "YYYY-MM-dd E HH:mm")
        )
    }

    fun toDetailDto(postMarkDown: String): PostDetailDto {

        return PostDetailDto(
            id = this.id,
            title = this.title,
            thumbnail = this.thumbnail,
            content = this.content,
            markDownContent = postMarkDown,
            username = this.user?.username ?: this.anonymousUsername,
            createdAt = TimeUtil.localDateTimeToString(this.createdAt, "YYYY-MM-dd E HH:mm")
        )
    }


    override fun toString(): String {
        return "Post(id=$id, title='$title', content='$content', thumbnail='$thumbnail', count=$count, user=$user)"
    }


}