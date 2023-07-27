package com.stella.free.core.blog.service

import com.stella.free.core.blog.dto.PostDetailDto
import com.stella.free.core.blog.dto.PostSaveDto
import com.stella.free.core.blog.entity.Post
import com.stella.free.core.blog.repo.PostRepository
import com.stella.free.global.config.security.UserPrincipal
import com.stella.free.global.service.FileUploader
import com.stella.free.global.util.logger
import com.vladsch.flexmark.html2md.converter.FlexmarkHtmlConverter
import com.vladsch.flexmark.util.data.MutableDataSet
import jakarta.annotation.PostConstruct
import jakarta.persistence.EntityNotFoundException
import net.datafaker.Faker
import net.datafaker.providers.base.Lorem
import net.datafaker.transformations.Field
import net.datafaker.transformations.JavaObjectTransformer
import net.datafaker.transformations.Schema
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.util.*
import java.util.function.Supplier


@Service
@Transactional
class PostService(
    private val postRepository: PostRepository,
    private val fileUploader: FileUploader,
) {

    private val log = logger()


    @PostConstruct
    fun init() {
        val posts = findAll()
        if (posts.isEmpty()) {
            generateDummyPosts(100)
        }
    }


    fun generateDummyPosts(size: Int) {
        val posts = mutableListOf<Post>()
        for (i in 0 until size) {
            val post = generateDummyPost((i + 1).toLong())
            //println(post)
            //posts.add(post)
        }

//        log.info("data initialized post: ${posts.size}")
//        postRepository.saveAll(posts)
    }

    fun generateDummyPost(id: Long): Post {

        val faker = Faker(Locale("ko"))
        val jTransformer = JavaObjectTransformer()

        val image = faker.internet().image()
        val lorem = faker.lorem()

        val schema: Schema<Any, Any> = Schema.of(
            //Field.field("id", Supplier { faker.number().positive() }),
            Field.field("title", Supplier { lorem.sentence() }),
            Field.field("content", Supplier { generateDummyPostContent(lorem, image) }),
            Field.field("thumbnail", Supplier { image }),
            Field.field("anonymousUsername", Supplier { faker.name().fullName() }),
        )

        val post =
            jTransformer.apply(Post::class.java, schema) as Post

        post.updateId(id)
        postRepository.save(post)
        println(post)

        return post
    }


    private fun generateDummyPostContent(lorem: Lorem, img:String ): String {

        val paragraph = lorem.paragraph(30)
        val doc = Document.createShell("")

        val imgTag =
            doc.createElement("img").attr("src", img).outerHtml()

        val div =
            doc.appendElement("div")

        div.append(imgTag)

        paragraph.split(".").forEach {
            div.appendElement("p").text(it)
        }
        return div.html()
    }


    fun findPostsByPage(pageable: Pageable): Page<Post> {

        return postRepository.findPostsByPage(pageable)
    }

    fun savePost(postSaveDto: PostSaveDto, principal: UserPrincipal?) {

        if (principal != null) {
            log.info("Saving post $principal")
        }

        log.info("Saving post without user => $postSaveDto")

        val thumbnail =
            Jsoup.parseBodyFragment(postSaveDto.content).getElementsByTag("img")
                .firstOrNull()?.attr("src")

        postRepository.save(postSaveDto.toEntity(principal?.user, thumbnail))
    }


    fun findAll(): MutableList<Post> {
        return postRepository.findAll()
    }

    fun findById(id: Long): PostDetailDto {

        val post = postRepository.findById(id)
            .orElseThrow { throw EntityNotFoundException() }

        val options = MutableDataSet()
        val postMarkDown =
            FlexmarkHtmlConverter.builder(options).build().convert(post.content)

        return post.toDetailDto(postMarkDown)
    }


    fun deleteById(id: Long) {

    }


    fun savePostImg(file: MultipartFile): String {

        return fileUploader.upload(file)
    }


}