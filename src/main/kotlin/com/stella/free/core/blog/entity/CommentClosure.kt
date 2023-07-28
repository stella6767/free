package com.stella.free.core.blog.entity

import com.stella.free.core.blog.dto.CommentCardDto
import com.stella.free.core.blog.dto.CommentTestDto
import com.stella.free.global.entity.BaseEntity
import com.stella.free.global.util.TimeUtil
import jakarta.persistence.*

@Entity
@Table(name = "comment_closure",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["id_ancestor","id_descendant"])
    ]
)
class CommentClosure(
    id:Long = 0,
    idAncestor: Comment,
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


    fun toCardDto(): CommentCardDto {

        return CommentCardDto(
            commentId = this.idDescendant.id,
            commentCloserId = this.id,
            depth = this.depth,
            idAncestor = this.idAncestor.id ?: 0,
            idDescendant = this.idDescendant.id,
            postId = this.idDescendant.post.id,
            content = this.idDescendant.content,
            username = this.idDescendant.user?.username ?: "익명",
            createdAt = TimeUtil.localDateTimeToString(this.createdAt, "hh:mm:ss a")
        )
    }


//    fun toCommentDto(childComments: List<CommentClosure>): CommentTestDto {
//
//        return CommentTestDto(
//            id = this.idDescendant.id,
//            childComments = childComments,
//            depth = this.depth,
//            postId = this.idDescendant.post.id,
//            username = this.idDescendant.user?.username ?: "익명",
//            content = this.idDescendant.content,
//            createdAt = TimeUtil.localDateTimeToString(this.createdAt, "hh:mm:ss a")
//        )
//    }

    override fun toString(): String {
        return "CommentClosure(id=$id, idAncestor=${idAncestor?.id}, idDescendant=${idDescendant.id}, depth=$depth)"
    }

}