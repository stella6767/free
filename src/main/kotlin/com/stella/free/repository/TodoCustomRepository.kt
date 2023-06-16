package com.stella.free.repository

import com.stella.free.entity.QTodo.*
import com.stella.free.entity.Todo
import com.stella.free.entity.User
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils

interface TodoCustomRepository {

    fun findTodos(pageable: Pageable, user: User): Page<Todo>

}



class TodoCustomRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
    private val em: EntityManager,
) : TodoCustomRepository {


    override fun findTodos(pageable: Pageable, user: User): Page<Todo> {

        val fetch = queryFactory
            .selectFrom(todo)
            .where(
                todo.user.eq(user)
            )
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .orderBy(todo.id.desc())
            .fetch()

        val countQuery = queryFactory
            .select(todo.count())
            .from(todo)
            .where(
                todo.user.eq(user)
            )
            .fetchOne() ?: 0


        return PageableExecutionUtils.getPage(
            fetch, pageable
        ) { countQuery }
    }

}