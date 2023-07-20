package com.stella.free.core.todo.entity

import com.stella.free.global.entity.BaseEntity
import com.stella.free.core.account.entity.User
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