package com.stella.free.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.stella.free.core.blog.dto.CommentCardDto
import com.stella.free.core.blog.dto.PostCardDto
import com.stella.free.core.blog.repo.CommentRepository
import com.stella.free.core.blog.repo.HashTagRepository
import com.stella.free.core.blog.repo.PostRepository
import com.stella.free.setup.RepositoriesTestConfig
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.data.domain.PageRequest
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
    private val hashTagRepository: HashTagRepository,
    private val mapper: ObjectMapper,
) {


    @Test
    fun getTagsTest(){

//        val post = postRepository.findPostById(96)
//
//        val tags =
//            post.map { it.postTags }.map { it.map { it.hashTag.name } }.orElse(listOf())
//
//        println(tags)

    }

    @Test
    fun findPostsByTagNameTest(){

        val posts =
            hashTagRepository.findPostsByTagName("123", PageRequest.of(0, 10))


        val map =
            posts.map { it.post }.map { PostCardDto.fromEntity(it,"") }



        //hashTagRepository.fetchJoinTest("123", PageRequest.of(0, 10))


    }

    @Test
    fun findPostsTest() {

        postRepository.findAll().forEach { println(it) }
    }

    @Test
    fun findCommentsByPostIdTest2() {

        val commentClosures =
            commentRepository.findCommentsByPostId(99)

        commentClosures.forEach { println(it) }

        println("================================================")

        val convertedCommentClosures =
            commentClosures.associateBy { it.idDescendant.id }.map { it.value }.toList()

        val rootComments =
            convertedCommentClosures.filter { it.depth == 0 }


        rootComments.map { CommentCardDto.fromEntity(it) }.forEach { println(it) }
        println("================================================")


        val list = rootComments.map {
            createTree(CommentCardDto.fromEntity(it), commentClosures.map { CommentCardDto.fromEntity(it)})
        }

        println("!!!!!!!!!!!!!!!!!!")

        println(mapper.writeValueAsString(list))

    }

    @Test
    fun findCommentsByPostIdTest() {


//        val commentClosures = commentRepository.findCommentsByPostId(99)
//            .associateBy { it.idDescendant.id }.map { it.value }.toList()
//
//        val commentCardDtos = commentClosures.filter { it.depth == 0 }.map {
//            createTree(it.toCardDto(), commentClosures.map { it.toCardDto() })
//        }


        val commentClosures =
            commentRepository.findCommentsByPostId(99)

        val commentCardDtos =
            commentClosures.associateBy { it.idDescendant.id }.map { it.value }.toList().filter { it.depth == 0 }.map {
            createTree(CommentCardDto.fromEntity(it), commentClosures.map { CommentCardDto.fromEntity(it) })
        }

        println("!!!!!!!!!!!!!!!!!!")
        println(mapper.writeValueAsString(commentCardDtos))


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

        //println("================================================")
        return parent
    }


    fun createTreeFunctional(parent: CommentCardDto, commentClosures: List<CommentCardDto>): CommentCardDto {
        val childComments =
            commentClosures.filter { it.idAncestor == parent.commentId && it.depth == parent.depth + 1 }
                .map { createTreeFunctional(it, commentClosures) }
                .toMutableList()
        return parent.copy(childComments = childComments)
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
    fun findCommentByAncestorCommentTest() {

        val comments =
            commentRepository.findCommentByAncestorComment(15)

        comments.forEach {
            println(it)
        }

    }


    //@Test
    fun findCommentsByBottomUpTest(){

        val comments =
            commentRepository.findCommentsByBottomUp(26, 2)

        comments.forEach { println(it) }

        println("================================================")

        val commentClosures =
            commentRepository.findCommentClosuresByBottomUp(40)

        println(commentClosures)

        println("================================================")

        val commentCardDto = if (commentClosures.size == 1) {
            CommentCardDto.fromEntity(commentClosures.first())
        }else{
            commentClosures.map { CommentCardDto.fromEntity(it)}.maxBy { it.depth }
        }

        println(commentCardDto)

    }


}