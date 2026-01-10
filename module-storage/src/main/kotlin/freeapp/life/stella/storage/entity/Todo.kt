package freeapp.life.stella.storage.entity


import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table


@Entity
@Table(name = "todo")
class Todo(
    id:Long = 0,
    content:String,
    status:Boolean = false,
) : BaseEntity(id = id) {


    @Column
    var content = content

    @Column
    var status = status


}