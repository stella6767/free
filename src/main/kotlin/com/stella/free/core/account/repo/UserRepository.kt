package com.stella.free.core.account.repo

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderer
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.stella.free.core.account.entity.User
import com.stella.free.global.util.getSingleResultOrNull
import com.stella.free.global.util.singleOrNullQuery
import jakarta.persistence.EntityManager
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>, UserCustomRepository {

}


interface UserCustomRepository {
    fun findByUsername(username: String): User?
    fun findByEmail(email: String): User?
    fun findUserById(id: Long): User?
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

        val jpaQuery = em.createQuery(render.query, User::class.java).apply {
            render.params.forEach { name, value ->
                setParameter(name, value)
            }
        }

        return jpaQuery.getSingleResultOrNull()
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

        val fetch = em.createQuery(render.query, User::class.java).apply {
            render.params.forEach { name, value ->
                setParameter(name, value)
            }
        }.getSingleResultOrNull()

        return fetch
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

        val fetch = em.createQuery(render.query, User::class.java).apply {
            render.params.forEach { name, value ->
                setParameter(name, value)
            }
        }.getSingleResultOrNull()

        return fetch
    }

}