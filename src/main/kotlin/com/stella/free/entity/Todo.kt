package com.stella.free.entity

import jakarta.persistence.*


@Entity
@Table(name = "todo")
class Todo(
    id:Long = 0,
    content:String,
    status:Boolean = false,
    user: User,
) : BaseEntity(id = id) {


    @Column
    var content = content

    @Column
    var status: Boolean = status


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user = user


}