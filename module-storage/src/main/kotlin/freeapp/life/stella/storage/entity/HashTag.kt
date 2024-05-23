package freeapp.life.stella.storage.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table


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