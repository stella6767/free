package com.stella.free.core.todo.repo


import com.linecorp.kotlinjdsl.query.spec.ExpressionOrderSpec
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import com.stella.free.core.account.entity.User

import com.stella.free.core.todo.entity.Todo
import jakarta.persistence.EntityManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils

interface TodoCustomRepository {

    fun findTodos(pageable: Pageable, user: User): Page<Todo>

}



class TodoCustomRepositoryImpl(
    private val queryFactory: SpringDataQueryFactory,
    private val em: EntityManager,
) : TodoCustomRepository {


    override fun findTodos(pageable: Pageable, user: User): Page<Todo> {

        val fetch = queryFactory
            .listQuery {
                select(entity(Todo::class))
                from(entity(Todo::class))
                offset(pageable.offset.toInt())
                limit(pageable.pageSize)
                where(
                    column(Todo::user).equal(user)
                )
                orderBy(ExpressionOrderSpec(column(Todo::id), false))
            }


        val count = queryFactory.singleQuery {
            select(count(column(Todo::id)))
            from(entity(Todo::class))
            where(
                column(Todo::user).equal(user)
            )
        }

        return PageableExecutionUtils.getPage(
            fetch, pageable
        ) { count }
    }

}