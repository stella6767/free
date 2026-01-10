package freeapp.life.stella.storage.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "comment")
class Comment(
    id: Long = 0,
    nickName:String,
    content: String,
    post: Post,
    user: User?,
) : BaseEntity(id) {

    @Column(name = "content", nullable = false, length = 1000)
    var content: String = content
        protected set

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
    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null
        protected set

    fun deleteByUser() {
        this.deletedAt = LocalDateTime.now()
    }

    override fun toString(): String {
        return "Comment(id= $id, content='$content')"
    }


}