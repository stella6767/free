package freeapp.life.stella.storage.repository

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderer
import freeapp.life.stella.storage.entity.CloudKey
import freeapp.life.stella.storage.entity.CloudObject
import freeapp.life.stella.storage.util.getCountByQuery
import freeapp.life.stella.storage.util.getResultWithPagination

import jakarta.persistence.EntityManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.jdbc.core.JdbcTemplate
import java.sql.Timestamp
import javax.sql.DataSource

interface CloudObjectRepository : JpaRepository<CloudObject, Long>, CloudObjectCustomRepository {
}

interface CloudObjectCustomRepository {
    fun bulkInsert(s3Objects: List<CloudObject>): Array<out IntArray>
    fun findObjectsByCloudKey(s3Key: CloudKey, pageable: Pageable): Page<CloudObject>
}

class CloudObjectCustomRepositoryImpl(
    dataSource: DataSource,
    private val renderer: JpqlRenderer,
    private val ctx: JpqlRenderContext,
    private val em: EntityManager,
) : CloudObjectCustomRepository {

    private val jdbcTemplate = JdbcTemplate(dataSource)


    override fun findObjectsByCloudKey(
        cloudKey: CloudKey,
        pageable: Pageable
    ): Page<CloudObject> {

        val query = jpql {
            select(
                entity(CloudObject::class),
            ).from(
                entity(CloudObject::class),
                fetchJoin(CloudObject::cloudKey),
            ).where(
                path(CloudObject::cloudKey).equal(cloudKey)
            ).orderBy(
                path(CloudObject::id).desc(),
            )
        }

        val render =
            renderer.render(query = query, ctx)

        val fetch =
            em.getResultWithPagination(render, CloudObject::class.java, pageable)

        val count = em.getCountByQuery(query, ctx, renderer)

        return PageableExecutionUtils.getPage(
            fetch, pageable
        ) { count }
    }


    override fun bulkInsert(s3Objects: List<CloudObject>): Array<out IntArray> {

        val sql = """
                INSERT INTO
                s3manager.s3_object
                (s3_key_id, object_key, name,  is_directory, size, last_modified, extension, created_at, updated_at)
                VALUES
                ( ?, ?, ?, ?, ?, ?, ?, ?, ? )
            """.trimIndent()

        try {

            val batchUpdate = jdbcTemplate.batchUpdate(
                sql,
                s3Objects,
                s3Objects.size
            ) { ps, argument ->

                val isDirectoryDB = if (argument.isDirectory) "Y" else "N"

                ps.setLong(1, argument.cloudKey.id) // 쿼리의 ?의 순서대로 1번으로 할당되며 해당 쿼리 ? 대신 치환
                ps.setString(2, argument.objectKey)
                ps.setString(3, argument.name)
                ps.setString(4, isDirectoryDB)
                ps.setLong(5, argument.size)
                ps.setTimestamp(6, Timestamp.valueOf(argument.lastModified))
                ps.setString(7, argument.extension)
                ps.setTimestamp(8, Timestamp.valueOf(argument.createdAt))
                ps.setTimestamp(9, Timestamp.valueOf(argument.updatedAt))
            }

            return batchUpdate

        } catch (e: Exception) {

            throw e
        }

    }


}
