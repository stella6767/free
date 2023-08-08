package com.stella.free.core.blog.repo

import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.querydsl.from.fetch
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.stella.free.core.account.entity.User
import com.stella.free.core.blog.entity.*
import jakarta.persistence.EntityManager
import jakarta.persistence.criteria.JoinType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.util.Assert

interface HashTagRepository : JpaRepository<HashTag, Long>, HashTagCustomRepository {
    fun findByName(name:String) : HashTag?

}


interface HashTagCustomRepository {
    fun savePostTag(postTag: PostTag): PostTag
    fun findTagsByPostId(postId: Long): List<PostTag>
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



}