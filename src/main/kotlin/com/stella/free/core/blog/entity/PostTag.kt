package com.stella.free.core.blog.entity

import com.stella.free.global.entity.BaseEntity
import jakarta.persistence.*


/**
 * 다대다테이블을 만들까 말까...하다 그냥 만들었음.
 * 쓸데없긴 한데,
 */



@Entity
@Table(name = "postTag")
class PostTag(
    post: Post,
    hashTag: HashTag,
) : BaseEntity() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    val post = post

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashTag_id")
    val hashTag = hashTag

}