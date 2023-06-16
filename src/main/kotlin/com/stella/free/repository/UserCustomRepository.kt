package com.stella.free.repository

import com.stella.free.entity.QUser.*
import com.stella.free.entity.User
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager

interface UserCustomRepository {


    fun findByUsername(username: String): User?
    fun findByEmail(email: String): User?
}



class UserCustomRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
    private val em: EntityManager,
) : UserCustomRepository {


    override fun findByUsername(username: String): User? {

        val fetch = queryFactory
            .selectFrom(user)
            .where(
                user.username.eq(username)
            )
            .fetchOne()

        return fetch

    }

    override fun findByEmail(email: String): User? {

        return queryFactory
            .selectFrom(user)
            .where(
                user.email.eq(email)
            )
            .fetchOne()

    }

}