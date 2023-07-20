package com.stella.free.core.account.repo


import com.linecorp.kotlinjdsl.query.spec.ExpressionOrderSpec
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import com.stella.free.core.account.entity.User

import jakarta.persistence.EntityManager

interface UserCustomRepository {
    fun findByUsername(username: String): User?
    fun findByEmail(email: String): User?
}



class UserCustomRepositoryImpl(
    private val queryFactory: SpringDataQueryFactory,
    private val em: EntityManager,
) : UserCustomRepository {


    override fun findByUsername(username: String): User? {

        val fetch = queryFactory
            .singleQuery {
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
            .singleQuery {
                select(entity(User::class))
                from(entity(User::class))
                where(
                    column(User::email).equal(email)
                )
            }

    }

}