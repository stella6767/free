package com.stella.free.core.blog.entity

import com.stella.free.global.entity.BaseEntity
import jakarta.persistence.*



@Entity
@Table(name = "hashTag")
class HashTag(
    name:String,
) : BaseEntity() {

    @Column(name = "name", nullable = false)
    val name: String = name
    override fun toString(): String {
        return "HashTag(name='$name')"
    }


}