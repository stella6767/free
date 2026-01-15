package freeapp.life.stella.storage.repository

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderer
import freeapp.life.stella.storage.entity.CloudKey
import freeapp.life.stella.storage.entity.User
import freeapp.life.stella.storage.util.getRecentSingleResultOrNull

import jakarta.persistence.EntityManager
import org.springframework.data.jpa.repository.JpaRepository

interface CloudKeyRepository : JpaRepository<CloudKey, Long>, CloudKeyCustomRepository {
}

interface CloudKeyCustomRepository {
    fun findKeyByUser(user: User): CloudKey?
}


class CloudKeyCustomRepositoryImpl(
    private val renderer: JpqlRenderer,
    private val ctx: JpqlRenderContext,
    private val em: EntityManager,
) : CloudKeyCustomRepository {


    override fun findKeyByUser(user: User): CloudKey? {

        val query = jpql {
            select(
                entity(CloudKey::class),
            ).from(
                entity(CloudKey::class),
                leftFetchJoin(CloudKey::user)
            ).where(
                and(
                    path(CloudKey::user).equal(user),
                    path(CloudKey::deletedAt).isNull(),
                )
            ).orderBy(
                path(CloudKey::id).desc()
            )
        }

        val render =
            renderer.render(query = query, ctx)

        return em.getRecentSingleResultOrNull(render, CloudKey::class.java)
    }

}
