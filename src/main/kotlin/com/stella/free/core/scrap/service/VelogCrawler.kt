package com.stella.free.core.scrap.service

import com.stella.free.core.scrap.dto.VelogPostDto
import com.stella.free.core.scrap.dto.VelogReadPostDto
import com.stella.free.core.scrap.dto.VelogUserTagDto
import com.stella.free.global.util.logger
import jakarta.servlet.ServletOutputStream
import org.springframework.graphql.client.HttpGraphQlClient
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import java.io.OutputStream
import java.nio.file.Paths
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream


@Component
class VelogCrawler(
    private val velogClient: WebClient,
    private val basicClient: WebClient
) {

    private val graphQlClient =
        HttpGraphQlClient.builder(velogClient).build()

    private val log = logger()

    fun parseAndDownlaodAsZip(username: String, outputStream: ServletOutputStream) {

        //todo zip 파일 좀 더 다양하게
        //비밀글 접근 가능
        val posts = this.getAllPosts(username).map {
            this.getPost(username, it.url_slug!!)
        }.filterNotNull()

        responseZipFromAttachments(outputStream, posts)
    }


    fun getAllPosts(username: String): MutableList<VelogPostDto> {

        var cursor: String? = null
        val allPosts = mutableListOf<VelogPostDto>()

        while (true) {
            val posts = getPosts(username, cursor)
            if (posts.size >= 20) {
                cursor = posts.last().id
            } else {
                break
            }
            allPosts.addAll(posts)
        }

        log.info("$username 님의 모든 글 ${allPosts.size} 개 가져옴")

        return allPosts
    }


    private fun getPosts(username: String, cursor: String?): MutableList<VelogPostDto> {

        return graphQlClient
            .document(velogPostsQuery)
            .operationName("Posts")
            .variables(mapOf("username" to username, "cursor" to cursor))
            .retrieve("posts")
            .toEntityList(VelogPostDto::class.java)
            .block() ?: throw NullPointerException("cant find velogPostsQuery Response data")

    }


    fun getPost(username: String, url_slug: String): VelogReadPostDto? {

        return graphQlClient
            .document(velogPostQuery)
            .operationName("ReadPost")
            .variables(mapOf("username" to username, "url_slug" to url_slug))
            .retrieve("post")
            .toEntity(VelogReadPostDto::class.java)
            .block()
    }


    fun getUserTags(username: String): VelogUserTagDto? {

        val userTags = graphQlClient
            .document(userTagsQuery)
            .operationName("UserTags")
            .variable("username", username)
            //.variable("cursor", 1)
            .retrieve("userTags")
            .toEntity(VelogUserTagDto::class.java)
            .block()

        return userTags
    }


    fun getImage(body: String): String {

        val regex =
            """!\[([^\]]*)]\((.*?.png|.jpeg|.jpg|.webp|.svg|.gif|.tiff)\s*("(?:.*[^"])")?\s*\)|!\[([^\]]*)]\((.*?)\s*("(?:.*[^"])")?\s*\)""".toRegex()

        return body.replace(regex) { matchResult ->
            val url = matchResult.groups[2]?.value ?: matchResult.groups[5]?.value
            if (url.isNullOrBlank()) return@replace matchResult.value
            val filename =
                url.replace(Regex("/\\s*$"), "").split('/').takeLast(2).joinToString("-").trim()
            val path = Paths.get("backup", "images", filename).toString()
//            //todo Io 작업 테스트를 코루틴, 스레드풀, 다양하게 확인간으..
//            basicClient.get().uri(url).retrieve().bodyToMono(String::class.java).block()?.let {
//
//                //it.toByteArray()
//                //println("확인---> $it")
//                //File(path).writeText(it)
//            }

            "![](/images/$url)"
        }


    }


    private fun responseZipFromAttachments(os: OutputStream, posts: List<VelogReadPostDto>) {

        ZipOutputStream(os).use { zos ->
            for (post in posts) {
                val zipEntry =
                    ZipEntry(post.createFileName())
                zos.putNextEntry(zipEntry)
                zos.write(post.createInfoBody().toByteArray())
                zos.closeEntry()
            }
        }

    }


}
