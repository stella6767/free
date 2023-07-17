package com.stella.free.service

import com.stella.free.config.security.UserPrincipal
import com.stella.free.dto.PostSaveDto
import com.stella.free.entity.Post
import com.stella.free.repository.PostRepository
import com.stella.free.util.logger
import jakarta.annotation.PostConstruct
import jakarta.persistence.EntityNotFoundException
import net.datafaker.Faker
import net.datafaker.transformations.Field
import net.datafaker.transformations.JavaObjectTransformer
import net.datafaker.transformations.Schema
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import java.util.function.Supplier

@Service
@Transactional
class PostService(
    private val postRepository: PostRepository,
) {

    private val log = logger()


    @PostConstruct
    fun init() {

        generateDummyPosts(100)

    }


    fun generateDummyPosts(size: Int) {
        //val posts = mutableListOf<Post>()
        for (i in 0 until size) {
            val post = generateDummyPost((i + 1).toLong())
            println(post)
            //posts.add(post)
        }

        //log.info("data initialized post: ${posts.size}")

        //postRepository.saveAll(posts)
    }

    fun generateDummyPost(id: Long): Post {

        val faker = Faker(Locale("ko"))
        val jTransformer = JavaObjectTransformer()

        val schema: Schema<Any, Any> = Schema.of(
            //Field.field("id", Supplier { faker.number().positive() }),
            Field.field("title", Supplier { faker.book().title() }),
            Field.field("content", Supplier { faker.famousLastWords().lastWords() }),
            Field.field("thumbnail", Supplier { faker.internet().image() }),
            Field.field("anonymousUsername", Supplier { faker.name().fullName() }),
        )

        val post =
            jTransformer.apply(Post::class.java, schema) as Post

        post.updateId(id)

        println(post)
        postRepository.save(post)


        return post
    }


    fun findPostsByPage(pageable: Pageable): Page<Post> {
        return postRepository.findPostsByPage(pageable)
    }

    fun savePost(postSaveDto: PostSaveDto) {

        val principal =
            SecurityContextHolder.getContext().authentication.principal as UserPrincipal

        log.info("Saving post $principal")


        val parser =
            Parser.builder().build()

        val document =
            parser.parse(postSaveDto.content)

        val htmlRenderer =
            HtmlRenderer.builder().build()


        val htmlText =
            htmlRenderer.render(document)




        //postSaveDto.toEntity(principal.user)

    }


    fun findById(id: Long): Post {

        val post = postRepository.findById(id)
            .orElseThrow { throw EntityNotFoundException() }

        return post
    }


    fun deleteById(id: Long) {

    }


}