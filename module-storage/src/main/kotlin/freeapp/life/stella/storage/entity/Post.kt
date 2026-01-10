package freeapp.life.stella.storage.entity

import com.fasterxml.jackson.annotation.JsonBackReference

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.springframework.util.StringUtils
import java.time.LocalDateTime

@Entity
@Table(name = "post")
class Post(
    id:Long = 0,
    title: String,
    content:String,
    thumbnail:String?,
    user: User,
    username:String,
) : BaseEntity(id=id) {

    @Column(nullable = false, length = 300)
    var title = title

    @Column(nullable = false, length = 100000)
    var content = content

    @Column(nullable = true, length = 1000)
    var thumbnail = thumbnail

    @ColumnDefault("0")
    var count = 0

    @Column(name = "username")
    var username = username

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    val user = user


    @JsonBackReference
    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    var postTags = mutableListOf<PostTag>()

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null
        protected set



    fun softDelete(now: LocalDateTime = LocalDateTime.now()) {
        this.deletedAt = now
    }

    fun update(
               title: String,
               content: String,
               username: String,
               createThumbnail: String?,
               postTags: List<PostTag>
    ) {

        this.title = title
        this.content = content
        if (StringUtils.hasLength(username)){
            this.username = username
        }else{
            this.username = "Anonymous"
        }
        this.thumbnail = createThumbnail
        this.postTags.clear()
        postTags.forEach {
            this.postTags.add(it)
        }
    }

    override fun toString(): String {
        return "Post(id=$id, title='$title', content='$content', thumbnail='$thumbnail', count=$count, user=$user)"
    }


}
