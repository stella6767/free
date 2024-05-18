package freeapp.life.todohtmx.entity


import jakarta.persistence.*


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