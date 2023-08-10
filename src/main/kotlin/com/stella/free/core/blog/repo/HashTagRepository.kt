package com.stella.free.core.blog.repo

import com.linecorp.kotlinjdsl.query.spec.ExpressionOrderSpec
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.querydsl.from.fetch
import com.linecorp.kotlinjdsl.querydsl.from.join
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.stella.free.core.blog.entity.*
import com.stella.free.global.util.singleOrNullQuery
import jakarta.persistence.EntityManager
import jakarta.persistence.criteria.JoinType
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.util.Assert

interface HashTagRepository : JpaRepository<HashTag, Long>, HashTagCustomRepository {
    fun findByName(name:String) : HashTag?

}


interface HashTagCustomRepository {
    fun savePostTag(postTag: PostTag): PostTag
    fun findTagsByPostId(postId: Long): List<PostTag>
    fun findPostsByTagName(tagName: String, pageable: Pageable): Page<PostTag>

}

class HashTagCustomRepositoryImpl(
    private val queryFactory: SpringDataQueryFactory,
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


    override fun findTagsByPostId(postId:Long): List<PostTag> {

        return queryFactory.listQuery {
            select(entity(PostTag::class))
            from(entity(PostTag::class))
            fetch(PostTag::post, JoinType.LEFT)
            fetch(PostTag::hashTag, JoinType.LEFT)
            where(
                nestedCol(col(PostTag::post), Post::id).equal(postId)
            )
        }

    }






    override fun findPostsByTagName(tagName: String, pageable: Pageable): Page<PostTag> {

        val fetch = queryFactory.listQuery {
            select(entity(PostTag::class))
            from(entity(PostTag::class))
            fetch(PostTag::post, JoinType.LEFT)
            fetch(PostTag::hashTag, JoinType.LEFT)
            fetch(Post::user, JoinType.LEFT)
            where(
                nestedCol(col(PostTag::hashTag), HashTag::name).equal(tagName)
            )
            offset(pageable.offset.toInt())
            limit(pageable.pageSize)
            orderBy(ExpressionOrderSpec(column(PostTag::id), false))
        }


        /**
         * entity를 조회하는 쿼리가 아니라면 ManyToOne 관계라 해도 fetch join X
         */
        val count = queryFactory.singleOrNullQuery {
            select(count(column(PostTag::id)))
            from(entity(PostTag::class))
            join(PostTag::post, JoinType.LEFT)
            join(PostTag::hashTag, JoinType.LEFT)
            join(Post::user, JoinType.LEFT)
            where(
                nestedCol(col(PostTag::hashTag),HashTag::name).equal(tagName)
            )
        }

        return PageableExecutionUtils.getPage(
            fetch, pageable
        ) { count ?: 0L }
    }



}