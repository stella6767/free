package com.stella.free.global.util


import com.linecorp.kotlinjdsl.dsl.jpql.Jpql
import com.linecorp.kotlinjdsl.dsl.jpql.JpqlDsl
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRendered
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderer
import jakarta.persistence.EntityManager
import jakarta.persistence.NoResultException
import jakarta.persistence.TypedQuery
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.regex.Pattern
import java.util.stream.Collectors


//inline fun <reified T> SpringDataQueryFactory.singleOrNullQuery(
//    noinline dsl: SpringDataCriteriaQueryDsl<T>.() -> Unit
//): T? =
//    try {
//        selectQuery(T::class.java, dsl).singleResult
//    } catch (e: NoResultException) {
//        null
//    }

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


fun getCountByQuery(
    countQuery: SelectQuery<*>,
    em:EntityManager,
    ctx: JpqlRenderContext,
    renderer: JpqlRenderer
): Long {


    val countQueryRenderer = renderer.render(query = countQuery, ctx)

    val count = em.createQuery(countQueryRenderer.query, Long::class.java).apply {
        countQueryRenderer.params.forEach { name, value ->
            setParameter(name, value)
        }
    }.resultList.size

    return count.toLong()
}


fun ClassPathResource.getMarkdownValueFormLocal(): String {

    val resource = this.inputStream
    val introduction =
        BufferedReader(InputStreamReader(resource)).use { reader ->
            reader.lines().collect(Collectors.joining("\n"))
        }

    val parser = Parser.builder().build()
    val document = parser.parse(introduction)
    val renderer = HtmlRenderer.builder().build()

    return renderer.render(document)
}

