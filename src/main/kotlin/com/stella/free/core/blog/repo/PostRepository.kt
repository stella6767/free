package com.stella.free.core.blog.repo

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery

import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRendered
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderer

import com.stella.free.core.account.entity.User
import com.stella.free.core.blog.entity.HashTag
import com.stella.free.core.blog.entity.Post
import com.stella.free.core.blog.entity.PostTag
import com.stella.free.global.util.getCountByQuery
import com.stella.free.global.util.getSingleResultOrNull

import jakarta.persistence.EntityManager
import jakarta.persistence.criteria.JoinType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.support.PageableExecutionUtils

interface PostRepository : JpaRepository<Post, Long>, PostCustomRepository {

}


interface PostCustomRepository {

    fun findPostsByPage(pageable: Pageable): Page<Post>
    fun findPostsByKeyword(keyword: String, pageable: Pageable): Page<Post>

    //fun findPostByIdAndPassword(id: Long, password: String): Post?
    fun findPostByIdAndUser(id: Long, user: User): Post?
    fun findPostById(id: Long): Post?
}


class PostCustomRepositoryImpl(
    private val renderer: JpqlRenderer,
    private val ctx: JpqlRenderContext,
    private val em: EntityManager,
) : PostCustomRepository {


    override fun findPostById(id: Long): Post? {


        val query = jpql {
            select(
                entity(Post::class),
            ).from(
                entity(Post::class),
                leftFetchJoin(Post::user),
            ).where(
                path(Post::id).equal(id)

            )
        }

        val render = renderer.render(query = query, ctx)

        val fetch = em.createQuery(render.query, Post::class.java).apply {
            render.params.forEach { name, value ->
                setParameter(name, value)
            }
        }.getSingleResultOrNull()


        return fetch
    }

    override fun findPostByIdAndUser(id: Long, user: User): Post? {


        val query = jpql {
            select(
                entity(Post::class),
            ).from(
                entity(Post::class),
                leftFetchJoin(Post::user),
            ).where(
                and(
                    path(Post::id).equal(id),
                    path(Post::user).equal(user),
                )

            )
        }

        val render = renderer.render(query = query, ctx)

        val fetch = em.createQuery(render.query, Post::class.java).apply {
            render.params.forEach { name, value ->
                setParameter(name, value)
            }
        }.getSingleResultOrNull()

        return fetch
    }


//    override fun findPostByIdAndPassword(id: Long, password: String): Post? {
//
//        val post = queryFactory
//            .singleOrNullQuery {
//                select(entity(Post::class))
//                from(entity(Post::class))
//                fetch(Post::user, JoinType.LEFT)
//                where(
//                    and(
//                        column(Post::password).equal(password),
//                        column(Post::id).equal(id),
//                    )
//                )
//            }
//
//        return post
//    }

    override fun findPostsByPage(pageable: Pageable): Page<Post> {

        val query = jpql {
            select(
                entity(Post::class),
            ).from(
                entity(Post::class),
                leftFetchJoin(Post::user),
            ).where(
                and(
                    path(Post::deletedAt).isNull(),
                )
            ).orderBy(
                path(Post::id).desc(),
            )
        }

        val render = renderer.render(query = query, ctx)

        val fetch = em.createQuery(render.query, Post::class.java).apply {
            render.params.forEach { name, value ->
                setParameter(name, value)
            }
        }

        fetch.firstResult = pageable.offset.toInt()
        fetch.maxResults = pageable.pageSize

        val count = getCountByQuery(query, em , ctx, renderer)


        return PageableExecutionUtils.getPage(
            fetch.resultList, pageable
        ) { count }
    }



    override fun findPostsByKeyword(keyword: String, pageable: Pageable): Page<Post> {


        val query = jpql {
            select(
                entity(Post::class),
            ).from(
                entity(Post::class),
                leftFetchJoin(Post::user),
            ).where(
                or(
                    path(Post::title).like("%$keyword%"),
                    path(Post::content).like("%$keyword%"),
                ).and(
                    path(Post::deletedAt).isNull()
                )
            ).orderBy(
                path(Post::id).desc(),
            )
        }

        val render = renderer.render(query = query, ctx)

        val fetch = em.createQuery(render.query, Post::class.java).apply {
            render.params.forEach { name, value ->
                setParameter(name, value)
            }
        }

        fetch.firstResult = pageable.offset.toInt()
        fetch.maxResults = pageable.pageSize

        val count = getCountByQuery(query, em , ctx, renderer)

        return PageableExecutionUtils.getPage(
            fetch.resultList, pageable
        ) { count ?: 0L }

    }


}
