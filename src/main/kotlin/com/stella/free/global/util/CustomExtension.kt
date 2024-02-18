package com.stella.free.global.util

import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.querydsl.SpringDataCriteriaQueryDsl
import jakarta.persistence.NoResultException
import jakarta.persistence.TypedQuery
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.regex.Pattern


inline fun <reified T> SpringDataQueryFactory.singleOrNullQuery(
    noinline dsl: SpringDataCriteriaQueryDsl<T>.() -> Unit
): T? =
    try {
        selectQuery(T::class.java, dsl).singleResult
    } catch (e: NoResultException) {
        null
    }

inline fun String.removeSpecialCharacters(): String {
    // 정규표현식 패턴: 숫자 및 문자를 제외한 모든 특수 문자 및 공백 (A-Za-z0-9는 숫자와 문자)
    val pattern = "[^A-Za-z0-9]"
    val p = Pattern.compile(pattern)
    val m = p.matcher(this)
    // 매칭되는 패턴을 빈 문자열로 치환하여 제거
    return m.replaceAll("")
}


inline fun <reified T> T.logger(): Logger {
    return LoggerFactory.getLogger(T::class.java)
}


fun <T> TypedQuery<T>.getSingleResultOrNull(): T? {

    return try {
        this.singleResult
    } catch (e: NoResultException) {
        null
    }
}