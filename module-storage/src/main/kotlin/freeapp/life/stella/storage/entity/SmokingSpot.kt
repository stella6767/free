package freeapp.life.stella.storage.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "smoking_spot")
class SmokingSpot(
    id: Long = 0,
    name: String,
    address: String,
) : BaseEntity(id = id) {


    @Column(name = "name", nullable = false)
    val name = name

    @Column(name = "address", nullable = false)
    val address = address


}