package freeapp.life.stella.storage.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
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


    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Post::class)
    @JoinColumn(name = "post_id")
    val post = post

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = HashTag::class)
    @JoinColumn(name = "hashTag_id")
    val hashTag = hashTag

}