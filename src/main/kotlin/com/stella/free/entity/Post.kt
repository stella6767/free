package com.stella.free.entity

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault

@Entity
@Table(name = "post")
class Post(
    id:Long = 0,
    title: String,
    content:String,
    thumbnail:String,
    user:User?,
) : BaseEntity(id=id) {

    @Column(nullable = false, length = 100)
    val title = title

    @Column(nullable = false, length = 100000)
    val content = content

    @Column(nullable = false, length = 1000)
    var thumbnail: String = thumbnail

    @ColumnDefault("0")
    var count = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    val user = user
    override fun toString(): String {
        return "Post(id=$id, title='$title', content='$content', thumbnail='$thumbnail', count=$count, user=$user)"
    }


}