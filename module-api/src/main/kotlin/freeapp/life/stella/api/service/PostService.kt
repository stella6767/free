package freeapp.life.stella.api.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.vladsch.flexmark.html2md.converter.FlexmarkHtmlConverter
import com.vladsch.flexmark.util.data.MutableDataSet
import freeapp.life.stella.api.config.security.UserPrincipal
import freeapp.life.stella.api.dto.PostCardDto
import freeapp.life.stella.api.dto.PostDetailDto
import freeapp.life.stella.api.dto.PostSaveDto
import freeapp.life.stella.api.dto.PostUpdateDto
import freeapp.life.stella.storage.entity.HashTag
import freeapp.life.stella.storage.entity.Post
import freeapp.life.stella.storage.entity.PostTag
import freeapp.life.stella.storage.repository.HashTagRepository
import freeapp.life.stella.storage.repository.PostRepository
import freeapp.life.stella.storage.repository.UserRepository
import jakarta.annotation.PostConstruct
import jakarta.persistence.EntityNotFoundException
import mu.KotlinLogging
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


@Service
class PostService(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val fileUploader: LocalFileUploaderImpl,
    private val hashTagRepository: HashTagRepository,
    private val velogCrawler: VelogCrawler,
    private val mapper: ObjectMapper
) {

    private val log = KotlinLogging.logger {  }

    @PostConstruct
    fun init() {
//        val posts = findAll()
//        if (posts.isEmpty()) {
//            generateDummyPosts(100)
//        }
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
    }

    @Transactional(readOnly = true)
    fun findPostsByPage(pageable: Pageable): Page<PostCardDto> {

        return postRepository.findPostsByPage(pageable).map {
            val text =
                Jsoup.parse(it.content).text()
            PostCardDto.fromEntity(it, text)
        }
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
            postRepository.findPostById(dto.id) ?: throw EntityNotFoundException()

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
            postRepository.findPostById(id) ?: throw EntityNotFoundException()

        post.softDelete()
    }

    @Transactional(readOnly = true)
    fun findPostsByKeyword(keyword: String, pageable: Pageable): Page<PostCardDto> {

        return postRepository.findPostsByKeyword(keyword, pageable).map {
            val text =
                Jsoup.parse(it.content).text()
            PostCardDto.fromEntity(it, text)
        }
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