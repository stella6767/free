package freeapp.life.stella.storage.repository


import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderer
import freeapp.life.stella.storage.entity.User
import freeapp.life.stella.storage.entity.UserVerify
import freeapp.life.stella.storage.util.getRecentSingleResultOrNull

import jakarta.persistence.EntityManager
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface UserVerifyRepository : JpaRepository<UserVerify, Long>, UserVerifyCustomRepository {

}


interface UserVerifyCustomRepository {
    fun findLatestUserVerifyByEmail(email: String, currentTime: LocalDateTime): UserVerify?
    fun findUserVerifyByCodeAndToken(verifyCode: String, verifyToken: String): UserVerify?
    fun findVerifyByUserEmailAndToken(email: String, verifyToken: String): UserVerify?
}


class UserVerifyCustomRepositoryImpl(
    private val renderer: JpqlRenderer,
    private val ctx: JpqlRenderContext,
    private val em: EntityManager,
) : UserVerifyCustomRepository {


    override fun findLatestUserVerifyByEmail(
        email: String,
        currentTime: LocalDateTime,
    ): UserVerify? {

        val query = jpql {
            select(
                entity(UserVerify::class)
            ).from(
                entity(UserVerify::class),
                fetchJoin(UserVerify::user)
            ).where(
                and(
                    path(UserVerify::verifiedAt).isNull(),
                    path(User::email).eq(email),
                    path(UserVerify::expiredAt).gt(currentTime)
                )
            ).orderBy(
                path(UserVerify::id).desc()
            )
        }


        val render = renderer.render(query = query, ctx)
        return em.getRecentSingleResultOrNull(render, UserVerify::class.java)
    }


    override fun findUserVerifyByCodeAndToken(
        verifyCode: String,
        verifyToken: String
    ): UserVerify? {

        val query = jpql {
            select(
                entity(UserVerify::class)
            ).from(
                entity(UserVerify::class),
                fetchJoin(UserVerify::user),
            ).where(
                and(
                    path(UserVerify::code).eq(verifyCode),
                    path(UserVerify::verifyToken).eq(verifyToken),
                    path(UserVerify::expiredAt).gt(LocalDateTime.now())
                )
            ).orderBy(
                path(UserVerify::id).desc()
            )
        }

        val render = renderer.render(query = query, ctx)
        return em.getRecentSingleResultOrNull(render, UserVerify::class.java)
    }


    override fun findVerifyByUserEmailAndToken(
        email: String,
        verifyToken: String
    ): UserVerify? {

        val query = jpql {
            select(
                entity(UserVerify::class)
            ).from(
                entity(UserVerify::class),
                fetchJoin(UserVerify::user),
            ).where(
                and(
                    path(User::email).eq(email),
                    path(UserVerify::verifyToken).eq(verifyToken),
                )
            ).orderBy(
                path(UserVerify::id).desc()
            )
        }

        val render = renderer.render(query = query, ctx)
        return em.getRecentSingleResultOrNull(render, UserVerify::class.java)
    }


}
