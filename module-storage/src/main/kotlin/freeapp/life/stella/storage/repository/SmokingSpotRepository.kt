package freeapp.life.stella.storage.repository

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderer
import freeapp.life.stella.storage.entity.SmokingSpot
import freeapp.life.stella.storage.util.getCountByQuery
import freeapp.life.stella.storage.util.getResultWithPagination
import jakarta.persistence.EntityManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.support.PageableExecutionUtils

interface SmokingSpotRepository : JpaRepository<SmokingSpot, Long>, SmokingSpotQueryRepository {
}


interface SmokingSpotQueryRepository {
}


class SmokingSpotQueryRepositoryImpl(
    private val renderer: JpqlRenderer,
    private val ctx: JpqlRenderContext,
    private val em: EntityManager,
) : SmokingSpotQueryRepository {



}
