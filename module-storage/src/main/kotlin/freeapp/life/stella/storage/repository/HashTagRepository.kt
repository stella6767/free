package freeapp.life.stella.storage.repository

import com.linecorp.kotlinjdsl.dsl.jpql.jpql

import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderer

import freeapp.life.stella.storage.entity.HashTag
import freeapp.life.stella.storage.entity.Post
import freeapp.life.stella.storage.entity.PostTag
import freeapp.life.stella.storage.util.getCountByQuery
import freeapp.life.stella.storage.util.getResultWithPagination
import jakarta.persistence.EntityManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.util.Assert

interface HashTagRepository : JpaRepository<HashTag, Long>, HashTagCustomRepository {
    fun findByName(name: String): HashTag?
}


interface HashTagCustomRepository {
    fun savePostTag(postTag: PostTag): PostTag
    fun findTagsByPostId(postId: Long): List<PostTag>
    fun findPostsByTagName(tagName: String, pageable: Pageable): Page<PostTag>

}

class HashTagCustomRepositoryImpl(
    private val renderer: JpqlRenderer,
    private val ctx: JpqlRenderContext,
    private val em: EntityManager,
) : HashTagCustomRepository {


    override fun savePostTag(postTag: PostTag): PostTag {
        Assert.notNull(postTag, "Entity must not be null")
        return if (postTag.id == 0L) {
            em.persist(postTag)
            postTag
        } else {
            em.merge(postTag)
        }
    }


    override fun findTagsByPostId(postId: Long): List<PostTag> {

        val query = jpql {
            select(
                entity(PostTag::class),
            ).from(
                entity(PostTag::class),
                leftFetchJoin(PostTag::post),
                leftFetchJoin(PostTag::hashTag),
            ).where(
                path(Post::id).equal(postId)

            )
        }

        val render = renderer.render(query = query, ctx)

        val fetch = em.createQuery(render.query, PostTag::class.java).apply {
            render.params.forEach { name, value ->
                setParameter(name, value)
            }
        }.resultList

        return fetch
    }


    override fun findPostsByTagName(tagName: String, pageable: Pageable): Page<PostTag> {

        val query = jpql {
            select(
                entity(PostTag::class),
            ).from(
                entity(PostTag::class),
                leftFetchJoin(PostTag::post),
                leftFetchJoin(PostTag::hashTag),
                leftFetchJoin(Post::user),
            ).where(
                path(HashTag::name).equal(tagName)
            ).orderBy(
                path(PostTag::id).asc(),
            )
        }


        val render = renderer.render(query = query, ctx)

        val fetch =
            em.getResultWithPagination(render, PostTag::class.java, pageable)
        val count = em.getCountByQuery(query, ctx, renderer)
        /**
         * entity를 조회하는 쿼리가 아니라면 ManyToOne 관계라 해도 fetch join X
         */


        return PageableExecutionUtils.getPage(
            fetch, pageable
        ) { count ?: 0L }
    }


}
