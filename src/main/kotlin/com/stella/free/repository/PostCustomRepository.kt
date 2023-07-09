package com.stella.free.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.stella.free.entity.Post
import com.stella.free.entity.QPost
import com.stella.free.entity.QPost.*
import com.stella.free.entity.QTodo
import jakarta.persistence.EntityManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils

interface PostCustomRepository {

    fun findPostsByPage(pageable: Pageable): Page<Post>

}


class PostCustomRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
    private val em: EntityManager,
): PostCustomRepository {


    override fun findPostsByPage(pageable: Pageable): Page<Post> {

        val fetch = queryFactory
            .selectFrom(post)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .orderBy(post.id.desc())
            .fetch()

        val countQuery = queryFactory
            .select(post.count())
            .from(post)
            .fetchOne() ?: 0


        return PageableExecutionUtils.getPage(
            fetch, pageable
        ) { countQuery }
    }


}
