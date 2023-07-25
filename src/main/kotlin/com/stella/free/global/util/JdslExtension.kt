package com.stella.free.global.util

import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.querydsl.SpringDataCriteriaQueryDsl
import jakarta.persistence.NoResultException


inline fun <reified T> SpringDataQueryFactory.singleOrNullQuery(
    noinline dsl: SpringDataCriteriaQueryDsl<T>.() -> Unit
): T? =
    try {
        selectQuery(T::class.java, dsl).singleResult
    } catch (e: NoResultException) {
        null
    }