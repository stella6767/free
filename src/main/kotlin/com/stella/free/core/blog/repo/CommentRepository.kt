package com.stella.free.core.blog.repo

import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.querydsl.from.fetch
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.stella.free.core.blog.entity.Comment
import com.stella.free.core.blog.entity.CommentClosure
import com.stella.free.core.blog.entity.Post
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository
import org.springframework.util.Assert
import kotlin.reflect.KProperty1

interface CommentRepository {

    fun findCommentByAncestorComment(idAncestor: Long): List<Comment>
    fun saveCommentClosure(idDescendant: Long, idAncestor: Long): Int
    fun saveComment(comment: Comment): Comment
    fun findCommentsByPostId(id: Long): List<Comment>
}




@Repository
class CommentRepositoryImpl(
    private val queryFactory: SpringDataQueryFactory,
    private val em: EntityManager,
) : CommentRepository {

    override fun findCommentsByPostId(id: Long): List<Comment> {

        return queryFactory.listQuery {
            select(entity(Comment::class))
            from(entity(Comment::class))
            fetch(Comment::post)
            fetch(Comment::user)
            where(
                nestedCol(col(Comment::post), Post::id).equal(id)
            )
        }

    }

    override fun saveComment(comment: Comment): Comment {
        Assert.notNull(comment, "Entity must not be null")
        return if (comment.id == 0L) {
            em.persist(comment)
            comment
        } else {
            em.merge(comment)
        }
    }



    override fun saveCommentClosure(idDescendant: Long, idAncestor:Long): Int {

        var executeCount = 0

        val parentComment = if (idAncestor == 0L ) null else idAncestor

        val sql = """
            INSERT INTO comment_closure
            ( id_ancestor, id_descendant, depth, updated_at, created_at)
            VALUES
            ($parentComment, $idDescendant, 0, now(), now())                       
        """.trimIndent()

        executeCount += em.createNativeQuery(sql).executeUpdate()

        if (parentComment != null){

            executeCount += em.createNativeQuery(
                """               
                INSERT into Comment_closure
                ( id_ancestor, id_descendant, depth, update_at, create_at)            
                SELECT 
                cc.id_ancestor,
                c.id_descendant,
                cc.depth + c.depth + 1,
                c.update_at,
                c.create_at
                from Comment_closure as cc, Comment_closure as c
                where  cc.id_descendant = $idAncestor and c.id_ancestor = $idDescendant
                
            """.trimIndent()
            ).executeUpdate()
        }

        return executeCount
    }



    override fun findCommentByAncestorComment(idAncestor: Long): List<Comment> {

        return queryFactory.listQuery {
            select(entity(Comment::class))
            from(entity(Comment::class))
            join(
                entity(CommentClosure::class),
                on(entity(Comment::class).equal(column(CommentClosure::idDescendant)))
            )
            where(
                nestedCol(col(CommentClosure::idAncestor as KProperty1<CommentClosure, Comment>), Comment::id).equal(idAncestor)
            )
        }

    }



}