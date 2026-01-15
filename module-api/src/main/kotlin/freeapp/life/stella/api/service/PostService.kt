package freeapp.life.stella.api.service

import com.fasterxml.jackson.databind.ObjectMapper
import freeapp.life.stella.api.config.security.UserPrincipal
import freeapp.life.stella.api.service.file.S3Service
import freeapp.life.stella.api.web.dto.PostCardDto
import freeapp.life.stella.api.web.dto.PostDetailDto
import freeapp.life.stella.api.web.dto.PostSaveDto
import freeapp.life.stella.api.web.dto.PostUpdateDto
import freeapp.life.stella.storage.entity.HashTag
import freeapp.life.stella.storage.entity.Post
import freeapp.life.stella.storage.entity.PostTag
import freeapp.life.stella.storage.repository.HashTagRepository
import freeapp.life.stella.storage.repository.PostRepository
import jakarta.annotation.PostConstruct
import jakarta.persistence.EntityNotFoundException
import mu.KotlinLogging
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import org.jsoup.Jsoup
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile


@Service
class PostService(
    private val postRepository: PostRepository,
    private val userService: UserService,
    private val s3Service: S3Service,
    private val hashTagRepository: HashTagRepository,
    private val velogCrawler: VelogCrawler,
    private val commentService: CommentService,
    private val mapper: ObjectMapper
) {

    private val log = KotlinLogging.logger { }

    private val folderName = "Blog"


    @PostConstruct
    fun init() {
        val posts = findAll()
        if (posts.isEmpty()) {
            generateDummyPosts(100)
        }
    }


    fun generateDummyPosts(size: Int) {
        //val posts = mutableListOf<Post>()
        val username = "stella6767"
        val user = userService.createDummyUser()
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
    fun savePost(postSaveDto: PostSaveDto, principal: UserPrincipal): Long {

        val thumbnail =
            createThumbnail(postSaveDto.content)

        val post =
            postRepository.save(postSaveDto.toEntity(principal.user, thumbnail))

        postSaveDto.postTags.forEach {
            val hashTag =
                hashTagRepository.findByName(it) ?: hashTagRepository.save(HashTag(name = it))
            hashTagRepository.savePostTag(PostTag(post, hashTag))
        }

        return post.id
    }

    private fun createThumbnail(content: String): String? {

        val thumbnail =
            Jsoup.parseBodyFragment(content).getElementsByTag("img")
                .firstOrNull()?.attr("src")

        return thumbnail
    }


    @Transactional
    fun updatePost(dto: PostUpdateDto, principal: UserPrincipal): Long {

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
            createThumbnail(dto.content),
            postTags
        )


        return post.id
    }


    @Transactional(readOnly = true)
    fun findAll(): MutableList<Post> {
        return postRepository.findAll()
    }


    @Transactional
    fun findPostDetailById(id: Long): PostDetailDto? {

        val post = postRepository.findByIdOrNull(id)

        return if (post != null) {
            post.count++

            val comments =
                commentService.findCommentsByPostId(post.id)

            PostDetailDto.fromEntity(post, comments)
        } else null
    }


    @Transactional(readOnly = true)
    fun findTagsByPostId(postId: Long): List<String> {

        return hashTagRepository.findTagsByPostId(postId).map {
            it.hashTag.name
        }
    }


    @Transactional(readOnly = true)
    fun findPostsByTagName(tagName: String, pageable: Pageable): Page<PostCardDto> {

        val posts =
            hashTagRepository
                .findPostsByTagName(tagName, pageable)
                .map { it.post }.map {
                    val text =
                        Jsoup.parse(it.content).text()
                    PostCardDto.fromEntity(it, text)
                }

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
    fun findPostCardDtos(keyword: String, pageable: Pageable): Page<PostCardDto> {


        val posts = if (StringUtils.hasLength(keyword)) {
            findPostsByKeyword(keyword, pageable)
        } else findPostsByPage(pageable)

        return posts
    }


    fun savePostImg(file: MultipartFile): String {

        return s3Service.putObject(file, folderName)
    }


}
