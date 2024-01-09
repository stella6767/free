package com.stella.free.core.blog.entity

import com.stella.free.core.account.entity.User
import com.stella.free.core.blog.dto.CommentCardDto
import com.stella.free.global.entity.BaseEntity
import com.stella.free.global.util.TimeUtil
import jakarta.persistence.*

@Entity
@Table(name = "comment")
class Comment(
    id: Long = 0,
    nickName:String,
    password: String?,
    content: String,
    post: Post,
    user: User?,
) : BaseEntity(id) {

    @Column(name = "content", nullable = false, length = 1000)
    var content: String = content
        protected set

    @Column(name = "password")
    var password = if (user != null) null else password

    @Column(name = "nickName")
    var nickName: String = nickName


    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Post::class)
    @JoinColumn(name = "post_id")
    var post: Post = post
        protected set

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User::class)
    @JoinColumn(name = "user_id")
    var user = user
        protected set


    fun deleteByUser(): String {
        val deletedComment = "deleted By User"
        this.content = deletedComment
        return deletedComment
    }


    override fun toString(): String {
        return "Comment(id= $id, content='$content')"
    }


}