package com.stella.free.core.blog.repo

import com.linecorp.kotlinjdsl.query.spec.ExpressionOrderSpec
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.linecorp.kotlinjdsl.spring.data.singleQuery

import com.stella.free.core.blog.entity.Post

import jakarta.persistence.EntityManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils

interface PostCustomRepository {

    fun findPostsByPage(pageable: Pageable): Page<Post>

}


class PostCustomRepositoryImpl(
    private val queryFactory: SpringDataQueryFactory,
    private val em: EntityManager,
): PostCustomRepository {


    override fun findPostsByPage(pageable: Pageable): Page<Post> {

        val fetch = queryFactory
            .listQuery {
                select(entity(Post::class))
                from(entity(Post::class))
                offset(pageable.offset.toInt())
                limit(pageable.pageSize)
                orderBy(ExpressionOrderSpec(column(Post::id), false))
            }

        val count = queryFactory.singleQuery {
            select(count(column(Post::id)))
            from(entity(Post::class))
        }


        return PageableExecutionUtils.getPage(
            fetch, pageable
        ) { count }
    }


}
