package com.stella.free.core.blog.repo

import com.linecorp.kotlinjdsl.dsl.jpql.jpql

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities.entity
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderer

import com.stella.free.core.account.entity.User
import com.stella.free.core.blog.entity.Comment
import com.stella.free.core.blog.entity.CommentClosure
import com.stella.free.core.blog.entity.Post
import com.stella.free.global.util.getSingleResultOrNull
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
    fun findCommentByIdAndPassword(id: Long, password: String): Comment?
}


@Repository
class CommentRepositoryImpl(
    private val renderer: JpqlRenderer,
    private val ctx: JpqlRenderContext,
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


    override fun findCommentByIdAndPassword(id: Long, password:String): Comment? {

        val query = jpql {
            select(
                entity(Comment::class),
            ).from(
                entity(Comment::class)
            ).where(
                and(
                    path(Comment::id).equal(id),
                    path(Comment::password).equal(password),
                )
            )
        }

        val render =
            renderer.render(query = query, ctx)

        val fetch = em.createQuery(render.query, Comment::class.java).apply {
            render.params.forEach { name, value ->
                setParameter(name, value)
            }
        }.getSingleResultOrNull()

        return fetch
    }


    override fun findCommentById(id: Long): Comment? {

        val query = jpql {
            select(
                entity(Comment::class),
            ).from(
                entity(Comment::class)
            ).where(
                path(Comment::id).equal(id)
            )
        }

        val render = renderer.render(query = query, ctx)

        val fetch = em.createQuery(render.query, Comment::class.java).apply {
            render.params.forEach { name, value ->
                setParameter(name, value)
            }
        }.getSingleResultOrNull()


        return fetch
    }


    override fun findCommentsByPostId(id: Long): List<CommentClosure> {

        val query = jpql {
            select(
                entity(CommentClosure::class),
            ).from(
                entity(CommentClosure::class),
                fetchJoin(CommentClosure::idDescendant),
                leftFetchJoin(Comment::user),
                fetchJoin(Comment::post),
            ).where(
                path(Post::id).equal(id)
            )
        }

        val render = renderer.render(query = query, ctx)

        val fetch = em.createQuery(render.query, CommentClosure::class.java).apply {
            render.params.forEach { name, value ->
                setParameter(name, value)
            }
        }.resultList

        return fetch
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

        val query = jpql {
            select(
                entity(Comment::class),
            ).from(
                entity(Comment::class),
                join(CommentClosure::idDescendant)
            ).where(
                path(CommentClosure::id).equal(idAncestor)
            )
        }

        val render = renderer.render(query = query, ctx)

        val fetch = em.createQuery(render.query, Comment::class.java).apply {
            render.params.forEach { name, value ->
                setParameter(name, value)
            }
        }.resultList


        return fetch
    }


    override fun findCommentsByBottomUp(commentId: Long, depth: Int): List<Comment> {


        val query = jpql {
            select(
                entity(Comment::class),
            ).from(
                entity(Comment::class),
                join(CommentClosure::idAncestor)
            ).where(
                and(
                    entity(CommentClosure::class, "b")(CommentClosure::idDescendant)(Comment::id).eq(commentId),
                    depth?.let { path(CommentClosure::depth).lessThan(depth) },
                )
            )
        }

        val render = renderer.render(query = query, ctx)

        val fetch = em.createQuery(render.query, Comment::class.java).apply {
            render.params.forEach { name, value ->
                setParameter(name, value)
            }
        }.resultList

        return fetch
    }


    override fun findCommentClosuresByBottomUp(commentId: Long, depth: Int?): List<CommentClosure> {


        val query = jpql {
            select(
                entity(CommentClosure::class),
            ).from(
                entity(CommentClosure::class),
                fetchJoin(CommentClosure::idDescendant),
                leftFetchJoin(Comment::user),
                fetchJoin(Comment::post),
            ).where(
                and(
                    entity(CommentClosure::class)(CommentClosure::idDescendant)(Comment::id).eq(commentId),
                    depth?.let { path(CommentClosure::depth).lessThan(depth) },
                )
            )
        }

        val render = renderer.render(query = query, ctx)

        val fetch = em.createQuery(render.query, CommentClosure::class.java).apply {
            render.params.forEach { name, value ->
                setParameter(name, value)
            }
        }.resultList


        return fetch
    }



}