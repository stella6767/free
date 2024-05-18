package freeapp.life.todohtmx.repository

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderer
import freeapp.life.todohtmx.entity.Todo
import freeapp.life.todohtmx.util.getCountByQuery
import freeapp.life.todohtmx.util.getDatasWithPagination
import jakarta.persistence.EntityManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.support.PageableExecutionUtils

interface TodoRepository : JpaRepository<Todo, Long>, TodoQueryRepository {
}


interface TodoQueryRepository {
    fun findTodosWithPage(pageable: Pageable): Page<Todo>
}


class TodoQueryRepositoryImpl(
    private val renderer: JpqlRenderer,
    private val ctx: JpqlRenderContext,
    private val em: EntityManager,
) : TodoQueryRepository {

    override fun findTodosWithPage(pageable: Pageable): Page<Todo> {

        val query = jpql {
            select(
                entity(Todo::class),
            ).from(
                entity(Todo::class),
            ).orderBy(
                path(Todo::id).desc(),
            )
        }

        val render = renderer.render(query = query, ctx)

        val fetch = em.getDatasWithPagination(render, Todo::class.java, pageable)
        val count = em.getCountByQuery(query, ctx, renderer)

        return PageableExecutionUtils.getPage(
            fetch, pageable
        ) { count }
    }


}
