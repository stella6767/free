package freeapp.life.stella.storage.repository

import com.linecorp.kotlinjdsl.dsl.jpql.jpql

import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderer

import freeapp.life.stella.storage.entity.User
import freeapp.life.stella.storage.entity.type.SignType
import freeapp.life.stella.storage.util.getResult
import freeapp.life.stella.storage.util.getSingleResultOrNull
import jakarta.persistence.EntityManager
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>, UserCustomRepository {

}


interface UserCustomRepository {
    fun findByUsername(username: String): User?
    fun findByEmail(email: String): User?
    fun findUserById(id: Long): User?
    fun findUserBySocialIdAndSignTypeAndStatus(socialId: String, signType: SignType, status: User.Status): User?
    fun findUsersByEmailAndSignTypeAndStatus(email: String, signType: SignType, status: User.Status): MutableList<User>
    fun findActiveUserByEmailAndSignType(email: String, signType: SignType): User?
}


class UserCustomRepositoryImpl(
    private val em: EntityManager,
    private val renderer: JpqlRenderer,
    private val ctx: JpqlRenderContext
) : UserCustomRepository {


    override fun findUserById(id: Long): User? {

        val query = jpql {
            select(
                entity(User::class),
            ).from(
                entity(User::class)
            ).where(
                path(User::id).equal(id)
            )
        }

        val render =
            renderer.render(query = query, ctx)

        return em.getSingleResultOrNull(render, User::class.java)
    }

    override fun findByUsername(username: String): User? {

        val query = jpql {
            select(
                entity(User::class),
            ).from(
                entity(User::class)
            ).where(
                path(User::username).equal(username)
            )
        }

        val render =
            renderer.render(query = query, ctx)

        return em.getSingleResultOrNull(render, User::class.java)
    }

    override fun findByEmail(email: String): User? {

        val query = jpql {
            select(
                entity(User::class),
            ).from(
                entity(User::class)
            ).where(
                path(User::email).equal(email)
            )
        }

        val render =
            renderer.render(query = query, ctx)

        return em.getSingleResultOrNull(render, User::class.java)
    }

    override fun findUserBySocialIdAndSignTypeAndStatus(
        socialId: String,
        signType: SignType,
        status: User.Status
    ): User? {

        val query = jpql {
            select(
                entity(User::class),
            ).from(
                entity(User::class),
            ).where(
                and(
                    path(User::socialId).equal(socialId),
                    path(User::signType).equal(signType),
                    path(User::status).equal(status),
                )
            )
        }


        val render = renderer.render(query = query, ctx)
        return em.getSingleResultOrNull(render, User::class.java)
    }

    override fun findActiveUserByEmailAndSignType(
        email: String,
        signType: SignType,
    ): User? {

        val query = jpql {
            select(
                entity(User::class),
            ).from(
                entity(User::class),
            ).where(
                and(
                    path(User::email).equal(email.trim()),
                    path(User::status).equal(User.Status.ACTIVATED),
                    path(User::signType).equal(signType),
                )
            )
        }

        val render = renderer.render(query = query, ctx)
        return em.getSingleResultOrNull(render, User::class.java)
    }


    override fun findUsersByEmailAndSignTypeAndStatus(
        email: String,
        signType: SignType,
        status: User.Status
    ): MutableList<User> {

        val query = jpql {
            select(
                entity(User::class),
            ).from(
                entity(User::class),
            ).where(
                and(
                    path(User::email).equal(email.trim()),
                    path(User::status).equal(status),
                    path(User::signType).equal(signType),
                )
            )
        }

        val render = renderer.render(query = query, ctx)
        return em.getResult(render, User::class.java)
    }


}