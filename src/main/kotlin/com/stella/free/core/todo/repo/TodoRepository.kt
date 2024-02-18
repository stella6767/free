package com.stella.free.core.todo.repo

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderer
import com.stella.free.core.account.entity.User
import com.stella.free.core.blog.entity.Post
import com.stella.free.core.todo.entity.Todo
import com.stella.free.global.util.getCountByQuery

import jakarta.persistence.EntityManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.support.PageableExecutionUtils

interface TodoRepository : JpaRepository<Todo, Long>, TodoCustomRepository {

}


interface TodoCustomRepository {

    fun findTodos(pageable: Pageable, user: User): Page<Todo>

}


class TodoCustomRepositoryImpl(
    private val renderer: JpqlRenderer,
    private val ctx: JpqlRenderContext,
    private val em: EntityManager,
) : TodoCustomRepository {


    override fun findTodos(pageable: Pageable, user: User): Page<Todo> {


        val query = jpql {
            select(
                entity(Todo::class),
            ).from(
                entity(Todo::class),
            ).where(
                and(
                    path(Todo::user).equal(user),
                )
            ).orderBy(
                path(Todo::id).desc(),
            )
        }

        val render = renderer.render(query = query, ctx)

        val fetch = em.createQuery(render.query, Todo::class.java).apply {
            render.params.forEach { name, value ->
                setParameter(name, value)
            }
        }

        fetch.firstResult = pageable.offset.toInt()
        fetch.maxResults = pageable.pageSize


        val count = getCountByQuery(query, em , ctx, renderer)


        return PageableExecutionUtils.getPage(
            fetch.resultList, pageable
        ) { count ?: 0 }
    }

}