package com.stella.free.core.blog.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.stella.free.core.account.repo.UserRepository
import com.stella.free.core.blog.dto.PostDetailDto
import com.stella.free.core.blog.dto.PostSaveDto
import com.stella.free.core.blog.dto.PostUpdateDto
import com.stella.free.core.blog.entity.HashTag
import com.stella.free.core.blog.entity.Post
import com.stella.free.core.blog.entity.PostTag
import com.stella.free.core.blog.repo.HashTagRepository
import com.stella.free.core.blog.repo.PostRepository
import com.stella.free.core.scrap.service.VelogCrawler
import com.stella.free.global.config.security.UserPrincipal
import com.stella.free.global.exception.PostNotFoundException
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
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
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
class PostService(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val fileUploader: FileUploader,
    private val hashTagRepository: HashTagRepository,
    private val velogCrawler: VelogCrawler,
    private val mapper: ObjectMapper
) {

    private val log = logger()

    @PostConstruct
    fun init() {
        val posts = findAll()
        if (posts.isEmpty()) {
            generateDummyPosts(100)
        }
        //generateDummyPosts(100)
    }


    fun generateDummyPosts(size: Int) {
        //val posts = mutableListOf<Post>()
        val username = "stella6767"
        val user = userRepository.getReferenceById(1)
        val parser = Parser.builder().build()
        val posts = velogCrawler.getPostsByUsername(username).map {
            val document = parser.parse(it.body)
            val renderer = HtmlRenderer.builder().build()
            val content = renderer.render(document)
            Post(
                title = it.title ?: "",
                content = content,
                thumbnail = createThumbnail(content),
                username = username,
                user = user
            )
        }

        postRepository.saveAll(posts)
//        for (i in 0 until size) {
//            generateDummyPost((i + 1).toLong())
//            //println(post)
//            //posts.add(post)
//        }
//        log.info("data initialized post: ${posts.size}")
//        postRepository.saveAll(posts)

    }


    fun generateDummyPost(id: Long): Post {

        val faker = Faker(Locale("ko"))
        val image = faker.internet().image()
        val lorem = faker.lorem()

        val user = userRepository.getReferenceById(1)

        val post = Post(
            id = id,
            title = lorem.sentence(),
            content = generateDummyPostContent(lorem, image),
            thumbnail = image,
            username = faker.name().fullName(),
            user = user
        )

        postRepository.save(post)

        return post
    }





    private fun generateDummyPostContent(lorem: Lorem, img: String): String {

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

    @Transactional(readOnly = true)
    fun findPostsByPage(pageable: Pageable): Page<Post> {

        return postRepository.findPostsByPage(pageable)
    }


    @Transactional
    fun savePost(postSaveDto: PostSaveDto, principal: UserPrincipal) {

        val thumbnail =
            createThumbnail(postSaveDto.content)

        val post =
            postRepository.save(postSaveDto.toEntity(principal.user, thumbnail))

        postSaveDto.postTags.forEach {
            val hashTag =
                hashTagRepository.findByName(it) ?: hashTagRepository.save(HashTag(name = it))
            hashTagRepository.savePostTag(PostTag(post, hashTag))
        }
    }

    private fun createThumbnail(content: String): String? {

        val thumbnail =
            Jsoup.parseBodyFragment(content).getElementsByTag("img")
                .firstOrNull()?.attr("src")

        return thumbnail
    }


    @Transactional
    fun updatePost(dto: PostUpdateDto, principal: UserPrincipal) {

        val post =
            postRepository.findPostById(dto.id) ?: throw PostNotFoundException()

        val postTags = dto.postTags.map {
            val hashTag =
                hashTagRepository.findByName(it) ?: hashTagRepository.save(HashTag(name = it))
            PostTag(post, hashTag)
        }

        post.update(
            dto.title,
            dto.content,
            dto.username,
            createThumbnail(dto.content), postTags
        )

    }


    @Transactional(readOnly = true)
    fun findAll(): MutableList<Post> {
        return postRepository.findAll()
    }


    @Transactional
    fun findPostDetailById(id: Long): PostDetailDto {

        val post = postRepository.findById(id)
            .orElseThrow { throw EntityNotFoundException() }

        post.count++

        val options = MutableDataSet()
        val postMarkDown =
            FlexmarkHtmlConverter.builder(options).build().convert(post.content)

        return PostDetailDto.fromEntity(post, postMarkDown)
    }


    @Transactional(readOnly = true)
    fun findTagsByPostId(postId: Long): List<String> {

        return hashTagRepository.findTagsByPostId(postId).map {
            it.hashTag.name
        }
    }


    @Transactional(readOnly = true)
    fun findPostsByTagName(tagName: String, pageable: Pageable): Page<Post> {

        val posts =
            hashTagRepository.findPostsByTagName(tagName, pageable).map { it.post }

        return posts
    }


    @Transactional
    fun deleteById(
        id: Long,
        principal: UserPrincipal
    ) {
        val post =
            postRepository.findPostById(id) ?: throw PostNotFoundException()

        post.softDelete()
    }

    @Transactional(readOnly = true)
    fun findPostsByKeyword(keyword: String, pageable: Pageable): Page<Post> {

        return postRepository.findPostsByKeyword(keyword, pageable)
    }


    @Transactional(readOnly = true)
    fun findPostById(id: Long): PostDetailDto? {
        val post = postRepository.findPostById(id)

        val tagNames =
            post?.postTags?.map { it.hashTag.name } ?: listOf()

        log.debug("tagNames?  $tagNames")

        return if (post != null) {
            PostDetailDto.fromEntity(post, "", mapper.writeValueAsString(tagNames))
        } else null
    }

    fun savePostImg(file: MultipartFile): String {

        return fileUploader.upload(file)
    }


}