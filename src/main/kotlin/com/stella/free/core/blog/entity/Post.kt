package com.stella.free.core.blog.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.stella.free.core.account.entity.User
import com.stella.free.core.blog.dto.PostCardDto
import com.stella.free.core.blog.dto.PostDetailDto
import com.stella.free.core.blog.dto.PostUpdateDto

import com.stella.free.global.entity.BaseEntity
import com.stella.free.global.util.TimeUtil
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import java.time.LocalDateTime

@Entity
@Table(name = "post")
class Post(
    id:Long = 0,
    title: String,
    content:String,
    thumbnail:String?,
    user: User?,
    anonymousUsername:String,
    anonymousPassword:String,
) : BaseEntity(id=id) {

    @Column(nullable = false, length = 100)
    var title = title

    @Column(nullable = false, length = 100000)
    var content = content

    @Column(nullable = true, length = 1000)
    var thumbnail = thumbnail

    //todo 조회수
    @ColumnDefault("0")
    var count = 0

    @Column(name = "anonymous_username")
    var anonymousUsername = anonymousUsername

    @Column(name = "anonymous_password")
    var password = anonymousPassword


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    val user = user


    @JsonBackReference
    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    var postTags = mutableListOf<PostTag>()

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null
        protected set

    fun updateId(id:Long){
        this.id = id
    }


    fun softDelete(now: LocalDateTime = LocalDateTime.now()) {
        this.deletedAt = now
        val timeToString =
            TimeUtil.localDateTimeToString(now, "YYYY-MM-dd E HH:mm")
        this.title = "삭제된 게시글입니다"
        this.content = "${timeToString} 경 삭제된 게시글입니다"
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

    fun toDetailDto(postMarkDown: String, tagNames:String = ""): PostDetailDto {

        return PostDetailDto(
            id = this.id,
            userId = this.user?.id,
            title = this.title,
            thumbnail = this.thumbnail,
            content = this.content,
            anonymousPassword = this.password,
            tagNames = tagNames,
            markDownContent = postMarkDown,
            username = this.user?.username ?: this.anonymousUsername,
            createdAt = TimeUtil.localDateTimeToString(this.createdAt, "YYYY-MM-dd E HH:mm"),
            deletedAt = this.deletedAt
        )
    }


    override fun toString(): String {
        return "Post(id=$id, title='$title', content='$content', thumbnail='$thumbnail', count=$count, user=$user)"
    }

    fun update(dto: PostUpdateDto) {

        this.title = dto.title
        this.password = dto.password
        this.content = dto.content
        //this.anonymousUsername = dto.anonymousUsername

        //this.postTags = dto.postTags

        TODO("Not yet implemented")
    }


}