package com.stella.free.core.blog.entity

import com.stella.free.core.account.entity.User
import com.stella.free.global.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "comment")
class Comment(
    id: Long = 0,
    content: String,
    post: Post,
    user: User?,
    anonymousUsername:String,
    anonymousPassword:String,
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


    @Column(name = "anonymous_username")
    var anonymousUsername = anonymousUsername
        protected set


    @Column(name = "anonymous_pw")
    var anonymousPassword = anonymousPassword
        protected set

}