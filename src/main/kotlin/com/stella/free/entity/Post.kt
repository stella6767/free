package com.stella.free.entity

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault

@Entity
@Table(name = "post")
class Post(
    title: String,
    content:String,
    user:User,
) : BaseEntity() {

    @Column(nullable = false, length = 100)
    val title = title

    @Column(nullable = false, length = 100000)
    val content = content

    @ColumnDefault("0")
    var count = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    val user = user

}