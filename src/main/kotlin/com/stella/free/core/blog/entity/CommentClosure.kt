package com.stella.free.core.blog.entity

import com.stella.free.global.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "comment_closure",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["id_ancestor","id_descendant"])
    ]
)
class CommentClosure(
    id:Long = 0,
    idAncestor: Comment? = null,
    idDescendant: Comment,
    depth:Int = 0,
) : BaseEntity(id) {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ancestor", nullable = true)
    var idAncestor = idAncestor
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_descendant", nullable = false)
    var idDescendant = idDescendant
        protected set

    @Column(name = "depth")
    var depth = depth
        protected set

    override fun toString(): String {
        return "CommentClosure(id=$id, idAncestor=$idAncestor, idDescendant=$idDescendant, depth=$depth)"
    }

}