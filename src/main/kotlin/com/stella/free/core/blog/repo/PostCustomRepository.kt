package com.stella.free.core.blog.repo

import com.linecorp.kotlinjdsl.query.spec.ExpressionOrderSpec
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.querydsl.from.fetch
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.stella.free.core.account.entity.User

import com.stella.free.core.blog.entity.Post
import com.stella.free.global.util.singleOrNullQuery

import jakarta.persistence.EntityManager
import jakarta.persistence.criteria.JoinType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils
import java.util.Optional

interface PostCustomRepository {

    fun findPostsByPage(pageable: Pageable): Page<Post>
    fun findPostsByKeyword(keyword: String, pageable: Pageable): Page<Post>

    fun findPostByIdAndPassword(id: Long, password: String): Post?
    fun findPostByIdAndUser(id: Long, user: User): Post?
    fun findPostById(id: Long): Optional<Post>
}


class PostCustomRepositoryImpl(
    private val queryFactory: SpringDataQueryFactory,
    private val em: EntityManager,
) : PostCustomRepository {



    override fun findPostById(id: Long): Optional<Post> {

        val post = queryFactory
            .singleOrNullQuery {
                select(entity(Post::class))
                from(entity(Post::class))
//                fetch(PostTag::post, JoinType.LEFT)
//                fetch(PostTag::hashTag, JoinType.LEFT)
                fetch(Post::user, JoinType.LEFT)
                where(
                    and(
                        column(Post::id).equal(id),
                        //nestedCol(col(PostTag::post), Post::id).equal(id)
                    )
                )
            }

        return Optional.ofNullable(post)
    }

    override fun findPostByIdAndUser(id:Long, user: User): Post? {

        return queryFactory
            .singleOrNullQuery {
                select(entity(Post::class))
                from(entity(Post::class))
                fetch(Post::user, JoinType.LEFT)
                where(
                    and(
                        column(Post::user).equal(user),
                        column(Post::id).equal(id),
                    )
                )
            }
    }


    override fun findPostByIdAndPassword(id: Long, password: String): Post? {

        val post = queryFactory
            .singleOrNullQuery {
                select(entity(Post::class))
                from(entity(Post::class))
                fetch(Post::user, JoinType.LEFT)
                where(
                    and(
                        column(Post::password).equal(password),
                        column(Post::id).equal(id),
                    )
                )
            }

        return post
    }

    override fun findPostsByPage(pageable: Pageable): Page<Post> {

        val fetch = queryFactory
            .listQuery {
                select(entity(Post::class))
                from(entity(Post::class))
                fetch(Post::user, JoinType.LEFT)
                offset(pageable.offset.toInt())
                limit(pageable.pageSize)
                orderBy(ExpressionOrderSpec(column(Post::id), false))
            }

        val count = queryFactory.singleOrNullQuery {
            select(count(column(Post::id)))
            from(entity(Post::class))
        }


        return PageableExecutionUtils.getPage(
            fetch, pageable
        ) { count ?: 0L }
    }

    override fun findPostsByKeyword(keyword: String, pageable: Pageable): Page<Post> {

        val fetch = queryFactory
            .listQuery {
                select(entity(Post::class))
                from(entity(Post::class))
                fetch(Post::user, JoinType.LEFT)
                where(
                    or(
                        column(Post::title).like("%$keyword%"),
                        column(Post::content).like("%$keyword%"),
                    )
                )
                offset(pageable.offset.toInt())
                limit(pageable.pageSize)
                orderBy(ExpressionOrderSpec(column(Post::id), false))
            }

        val count = queryFactory.singleOrNullQuery {
            select(count(column(Post::id)))
            from(entity(Post::class))
            where(
                or(
                    column(Post::title).like("%$keyword%"),
                    column(Post::content).like("%$keyword%"),
                )
            )
        }

        return PageableExecutionUtils.getPage(
            fetch, pageable
        ) { count ?: 0L }

    }


}
