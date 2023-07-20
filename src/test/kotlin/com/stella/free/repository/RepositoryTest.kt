package com.stella.free.repository

import com.stella.free.core.blog.repo.PostRepository
import com.stella.free.setup.RepositoriesTestConfig
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.TestConstructor
import org.springframework.transaction.annotation.Transactional


@Transactional
@Import(*[RepositoriesTestConfig::class]) //, TestDataSource::class
//@ActiveProfiles("test")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //Any = h2
@DataJpaTest
class RepositoryTest(
    private val postRepository: PostRepository,
) {


    @Test
    fun findPostsTest(){

        postRepository.findAll().forEach { println(it) }

    }

}