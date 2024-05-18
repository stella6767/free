package freeapp.life.todohtmx.util

import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRendered
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderer
import freeapp.life.todohtmx.entity.Todo
import jakarta.persistence.EntityManager
import org.springframework.data.domain.Pageable

inline fun EntityManager.getCountByQuery(
    countQuery: SelectQuery<*>,
    ctx: JpqlRenderContext,
    renderer: JpqlRenderer
): Long {

    val countQueryRenderer = renderer.render(query = countQuery, ctx)

    val count = this.createQuery(countQueryRenderer.query, Long::class.java).apply {
        countQueryRenderer.params.forEach { name, value ->
            setParameter(name, value)
        }
    }.resultList.size

    return count.toLong()
}


inline fun <reified T : Any> EntityManager.getDatasWithPagination(
    render: JpqlRendered,
    type: Class<T>,
    pageable: Pageable
): MutableList<T> {

    val fetch = this.createQuery(render.query, type).apply {
        render.params.forEach { name, value ->
            setParameter(name, value)
        }
    }

    fetch.firstResult = pageable.offset.toInt()
    fetch.maxResults = pageable.pageSize

    return fetch.resultList
}