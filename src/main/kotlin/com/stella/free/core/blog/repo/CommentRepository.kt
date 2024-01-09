package com.stella.free.core.blog.repo

import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.querydsl.from.fetch
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.linecorp.kotlinjdsl.spring.data.querydsl.SpringDataCriteriaQueryDsl
import com.stella.free.core.blog.entity.Comment
import com.stella.free.core.blog.entity.CommentClosure
import com.stella.free.core.blog.entity.Post
import com.stella.free.global.util.singleOrNullQuery
import jakarta.persistence.EntityManager
import jakarta.persistence.criteria.JoinType
import org.springframework.stereotype.Repository
import org.springframework.util.Assert
import kotlin.reflect.KProperty1

interface CommentRepository {

    fun findCommentByAncestorComment(idAncestor: Long): List<Comment>
    fun saveCommentClosure(idDescendant: Long, idAncestor: Long): Int
    fun saveComment(comment: Comment): Comment
    fun findCommentsByPostId(id: Long): List<CommentClosure>

    //fun findCommentsByPostIdTest(id: Long): List<CommentClosure>
    fun findCommentsByBottomUp(commentId: Long, depth: Int): List<Comment>
    fun findCommentClosuresByBottomUp(commentId: Long, depth: Int? = null): List<CommentClosure>
    fun findCommentById(id: Long): Comment?
}


@Repository
class CommentRepositoryImpl(
    private val queryFactory: SpringDataQueryFactory,
    private val em: EntityManager,
) : CommentRepository {

//    override fun findCommentsByPostId(id: Long): List<Comment> {
//
//        return queryFactory.listQuery {
//            select(entity(Comment::class))
//            from(entity(Comment::class))
//            fetch(Comment::post)
//            fetch(Comment::user)
//            where(
//                nestedCol(col(Comment::post), Post::id).equal(id)
//            )
//        }
//    }


    override fun findCommentById(id: Long): Comment? {

        return queryFactory
            .singleOrNullQuery {
                select(entity(Comment::class))
                from(entity(Comment::class))
                where(
                    column(Comment::id).equal(id),
                    )
            }
    }


    override fun findCommentsByPostId(id: Long): List<CommentClosure> {

        //todo migration

        return queryFactory.listQuery {
            select(entity(CommentClosure::class))
            from(entity(CommentClosure::class))
            fetch(CommentClosure::idDescendant)
            fetch(Comment::user)
            fetch(Comment::post)
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


    override fun saveCommentClosure(idDescendant: Long, idAncestor: Long): Int {

        var executeCount = 0

        val parentComment = if (idAncestor == 0L) null else idAncestor

        val sql = """
            INSERT INTO comment_closure
            ( id_ancestor, id_descendant, depth, updated_at, created_at)
            VALUES
            ($idDescendant, $idDescendant, 0, now(), now())                       
        """.trimIndent()

        executeCount += em.createNativeQuery(sql).executeUpdate()

        if (parentComment != null) {

            executeCount += em.createNativeQuery(
                """               
                INSERT into comment_closure
                ( id_ancestor, id_descendant, depth, updated_at, created_at)            
                SELECT 
                cc.id_ancestor,
                c.id_descendant,
                cc.depth + c.depth + 1,
                c.updated_at,
                c.created_at
                from comment_closure as cc, Comment_closure as c
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
                nestedCol(col(CommentClosure::idAncestor), Comment::id).equal(idAncestor)
            )
        }

    }


    override fun findCommentsByBottomUp(commentId: Long, depth: Int): List<Comment> {

        return queryFactory.listQuery<Comment> {
            select(entity(Comment::class))
            from(entity(Comment::class))
            join(
                entity(CommentClosure::class),
                on(entity(Comment::class).equal(column(CommentClosure::idAncestor)))
            )
            where(
                findByDepthAndCommentId(commentId, depth)
            )
        }
    }


    override fun findCommentClosuresByBottomUp(commentId: Long, depth: Int?): List<CommentClosure> {

        return queryFactory.listQuery {
            select(entity(CommentClosure::class))
            from(entity(CommentClosure::class))
            fetch(CommentClosure::idDescendant)
            fetch(Comment::user)
            fetch(Comment::post)
            where(
                findByDepthAndCommentId(commentId, depth)
            )
        }
    }

    private fun <T> SpringDataCriteriaQueryDsl<T>.findByDepthAndCommentId(
        commentId: Long,
        depth: Int?,
    ): PredicateSpec {
        //.and(column(CategoryClosure::depth).equal(depth))
        return and(
            nestedCol(col(CommentClosure::idDescendant), Comment::id).equal(
                commentId
            ),
            depth?.let { column(CommentClosure::depth).lessThan(depth) },
        )
    }


}