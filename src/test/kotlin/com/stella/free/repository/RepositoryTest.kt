package com.stella.free.repository

import com.stella.free.core.blog.entity.Comment
import com.stella.free.core.blog.entity.CommentClosure
import com.stella.free.core.blog.repo.CommentRepository
import com.stella.free.core.blog.repo.PostRepository
import com.stella.free.setup.RepositoriesTestConfig
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor
import org.springframework.transaction.annotation.Transactional


@Transactional
@Import(*[RepositoriesTestConfig::class]) //, TestDataSource::class
@ActiveProfiles("test")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //Any = h2
@DataJpaTest
class RepositoryTest(
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository,
) {


    @Test
    fun findPostsTest(){

        postRepository.findAll().forEach { println(it) }
    }

    @Test
    fun findCommentsByPostIdTest(){

        val commentClosures =
            commentRepository.findCommentsByPostId(99)

        commentClosures.forEach { println(it) }

        val convertedCommentClosures =
            hashMapOf<Long, CommentClosure>()
        commentClosures.forEach {
            convertedCommentClosures.put(it.idDescendant.id, it)
        }


        val closureMap =
            commentClosures.associateBy { it.idDescendant.id }

        println(convertedCommentClosures)

        println("================================================")

        println(closureMap)

        println("================================================")

        val closureList =
            closureMap.map { it.value }.toList().map { it.toCardDto() }

        println(closureList)

        val groupBy =
            closureList.groupBy { it.idAncestor }

        println("================================================")


        for (entry in groupBy.entries.iterator()) {

            for (commentCardDto in entry.value) {
                if (commentCardDto.depth == 0) println("" + commentCardDto)
                else println(" " + commentCardDto)
            }

            println("========================보소======================")

        }


    }



    /**
     *
     *  //바텀 업
     *  SELECT c.*
    FROM category  AS c
    JOIN category_closure AS t
    ON c.id = t.id_ancestor
    WHERE t.id_descendant  = 11;


    //Top down
    SELECT c.*
    FROM category  AS c
    JOIN category_closure AS t
    ON c.id = t.id_descendant
    WHERE t.id_ancestor  = 1;
     */

    @Test
    fun findCommentByAncestorCommentTest(){
        val comments =
            commentRepository.findCommentByAncestorComment(15)

        comments.forEach {
            println(it)
        }

    }



}