package com.stella.free.core.account.repo

import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.stella.free.core.account.entity.User
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
    private val queryFactory: SpringDataQueryFactory,
    private val em: EntityManager,
) : UserCustomRepository {


    override fun findUserById(id:Long): User? {

        return queryFactory
            .singleOrNullQuery {
                select(entity(User::class))
                from(entity(User::class))
                where(
                    column(User::id).equal(id)
                )
            }

    }

    override fun findByUsername(username: String): User? {

        val fetch = queryFactory
            .singleOrNullQuery {
                select(entity(User::class))
                from(entity(User::class))
                where(
                    column(User::username).equal(username)
                )
            }

        return fetch

    }

    override fun findByEmail(email: String): User? {


        return queryFactory
            .singleOrNullQuery {
                select(entity(User::class))
                from(entity(User::class))
                where(
                    column(User::email).equal(email)
                )
            }

    }

}