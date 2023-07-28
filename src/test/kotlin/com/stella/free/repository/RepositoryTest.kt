package com.stella.free.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.stella.free.core.blog.dto.CommentCardDto
import com.stella.free.core.blog.dto.CommentTestDto
import com.stella.free.core.blog.entity.CommentClosure
import com.stella.free.core.blog.repo.CommentRepository
import com.stella.free.core.blog.repo.PostRepository
import com.stella.free.setup.RepositoriesTestConfig
import org.jetbrains.kotlin.build.deserializeFromPlainText
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

        println("================================================")

        val closureMap =
            commentClosures.associateBy { it.idDescendant.id }.map { it.value }.toList()

        val rootComments =
            closureMap.filter { it.depth == 0 }


        rootComments.map { it.toCardDto() }.forEach { println(it)}
        println("================================================")


        val list = rootComments.map {
            createTreeFunctional(it.toCardDto(), commentClosures.map { it.toCardDto() })
        }


        println("!!!!!!!!!!!!!!!!!!")


//        list.forEach {
//            println(it)
//        }

        println(ObjectMapper().writeValueAsString(list))




//        val buildCommentTree =
//            buildCommentTree(commentClosures)
//
//
//        println(buildCommentTree)


//        val convertedCommentClosures =
//            hashMapOf<Long, CommentClosure>()
//        commentClosures.forEach {
//            convertedCommentClosures.put(it.idDescendant.id, it)
//        }
//        println(convertedCommentClosures)
//
//        val closureMap =
//            commentClosures.associateBy { it.idDescendant.id }
//
//        println("================================================")
//
//        println(closureMap)


//        println("================================================")
//
//        val closureList =
//            closureMap.map { it.value }.toList().map { it.toCardDto() }
//
//        println(closureList)
//
//        println(  ObjectMapper().writeValueAsString(closureList))


//        val groupBy =
//            closureList.groupBy { it.idAncestor }
//
//        println("================================================")
//
//
//        for (entry in groupBy.entries.iterator()) {
//
//            for (commentCardDto in entry.value) {
//                if (commentCardDto.depth == 0) println("" + commentCardDto)
//                else println(" " + commentCardDto)
//            }
//
//            println("========================보소======================")
//
//        }


    }



    fun createTree(parent: CommentCardDto, commentClosures: List<CommentCardDto>): CommentCardDto {

        for (commentClosure in commentClosures) {
            if (commentClosure.idAncestor == parent.commentId) {
                //println(commentClosure)
                if (commentClosure.depth == 1) {
                    println(commentClosure)
                    parent.childComments.add(commentClosure)
                    createTree(commentClosure, commentClosures)
                }
                //parent.childComments.add(createTree(commentClosure, commentClosures.slice(1..commentClosures.size-1)))
            }
        }

        println("================================================")
        return parent
    }


    fun createTreeFunctional(parent: CommentCardDto, commentClosures: List<CommentCardDto>): CommentCardDto {
        val childComments =
            commentClosures.filter { it.idAncestor == parent.commentId && it.depth == parent.depth + 1 }
            .map { createTree(it, commentClosures) }
            .toMutableList()
        return parent.copy(childComments = childComments)
    }

//    fun buildCommentTree(commentClosures: List<CommentClosure>): List<CommentTestDto> {
//
//
//        val testDtoList = mutableListOf<CommentTestDto>()
//
//        for (commentClosure in commentClosures) {
//            if (commentClosure.depth == 0) {
//                testDtoList.add()
//            }
//
//        }
//
//        TODO()
//    }


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