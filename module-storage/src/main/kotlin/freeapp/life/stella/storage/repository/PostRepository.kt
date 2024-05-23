package freeapp.life.stella.storage.repository

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderer
import freeapp.life.stella.storage.entity.Post
import freeapp.life.stella.storage.entity.User
import freeapp.life.stella.storage.util.getCountByQuery
import freeapp.life.stella.storage.util.getResultWithPagination
import freeapp.life.stella.storage.util.getSingleResultOrNull
import jakarta.persistence.EntityManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.support.PageableExecutionUtils

interface PostRepository : JpaRepository<Post, Long>, PostCustomRepository {

}


interface PostCustomRepository {

    fun findPostsByPage(pageable: Pageable): Page<Post>
    fun findPostsByKeyword(keyword: String, pageable: Pageable): Page<Post>

    //fun findPostByIdAndPassword(id: Long, password: String): Post?
    fun findPostByIdAndUser(id: Long, user: User): Post?
    fun findPostById(id: Long): Post?
    fun fromSubQueryTest()
}


class PostCustomRepositoryImpl(
    private val renderer: JpqlRenderer,
    private val ctx: JpqlRenderContext,
    private val em: EntityManager,
) : PostCustomRepository {


    override fun findPostById(id: Long): Post? {


        val query = jpql {
            select(
                entity(Post::class),
            ).from(
                entity(Post::class),
                leftFetchJoin(Post::user),
            ).where(
                path(Post::id).equal(id)

            )
        }

        val render = renderer.render(query = query, ctx)

        return em.getSingleResultOrNull(render, Post::class.java)
    }

    override fun findPostByIdAndUser(id: Long, user: User): Post? {


        val query = jpql {
            select(
                entity(Post::class),
            ).from(
                entity(Post::class),
                leftFetchJoin(Post::user),
            ).where(
                and(
                    path(Post::id).equal(id),
                    path(Post::user).equal(user),
                )

            )
        }

        val render = renderer.render(query = query, ctx)

        return em.getSingleResultOrNull(render, Post::class.java)
    }


//    override fun findPostByIdAndPassword(id: Long, password: String): Post? {
//
//        val post = queryFactory
//            .singleOrNullQuery {
//                select(entity(Post::class))
//                from(entity(Post::class))
//                fetch(Post::user, JoinType.LEFT)
//                where(
//                    and(
//                        column(Post::password).equal(password),
//                        column(Post::id).equal(id),
//                    )
//                )
//            }
//
//        return post
//    }

    override fun findPostsByPage(pageable: Pageable): Page<Post> {

        val query = jpql {
            select(
                entity(Post::class),
            ).from(
                entity(Post::class),
                leftFetchJoin(Post::user),
            ).where(
                and(
                    path(Post::deletedAt).isNull(),
                )
            ).orderBy(
                path(Post::id).desc(),
            )
        }

        val render = renderer.render(query = query, ctx)
        val fetch = em.getResultWithPagination(render, Post::class.java, pageable)
        val count = em.getCountByQuery(query, ctx, renderer)

        return PageableExecutionUtils.getPage(
            fetch, pageable
        ) { count }
    }


    override fun findPostsByKeyword(keyword: String, pageable: Pageable): Page<Post> {


        val query = jpql {
            select(
                entity(Post::class),
            ).from(
                entity(Post::class),
                leftFetchJoin(Post::user),
            ).where(
                or(
                    path(Post::title).like("%$keyword%"),
                    path(Post::content).like("%$keyword%"),
                ).and(
                    path(Post::deletedAt).isNull()
                )
            ).orderBy(
                path(Post::id).desc(),
            )
        }

        val render = renderer.render(query = query, ctx)
        val fetch = em.getResultWithPagination(render, Post::class.java, pageable)
        val count = em.getCountByQuery(query, ctx, renderer)

        return PageableExecutionUtils.getPage(
            fetch, pageable
        ) { count ?: 0L }

    }

    data class DerivedEntity(
        val id: Long,
        //val count: Long,
    )

    override fun fromSubQueryTest() {

        val query = jpql {

            val subQuery = select<DerivedEntity>(
                path(Post::id).`as`(expression("id")),
                //count(Post::id).`as`(expression("count")),
            ).from(
                entity(Post::class),
                //join(Post::class).on(path(HashTag::id).equal(path(Comment::id)))
            )

            select(
                entity(DerivedEntity::class),
            ).from(
                subQuery.asEntity()
            )
        }


        val render = renderer.render(query = query, ctx)

        val fetch = em.createQuery(render.query, Long::class.java).apply {
            render.params.forEach { name, value ->
                setParameter(name, value)
            }
        }.resultList

        println(fetch)


//        var jpql =
//            "SELECT sub.id FROM (SELECT p.id FROM Post p) sub"
//
//        val singleResult = em.createQuery(jpql, Long::class.java).resultList
//
//        println(singleResult)

    }




}
