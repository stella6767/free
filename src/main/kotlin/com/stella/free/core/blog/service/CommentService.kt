package com.stella.free.core.blog.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.stella.free.core.account.repo.UserRepository
import com.stella.free.core.blog.dto.CommentCardDto
import com.stella.free.core.blog.dto.CommentSaveDto
import com.stella.free.core.blog.entity.Comment
import com.stella.free.core.blog.repo.CommentRepository
import com.stella.free.core.blog.repo.PostRepository
import com.stella.free.global.util.logger
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val mapper: ObjectMapper
) {

    private val log = logger()

    @Transactional
    fun saveComment(dto: CommentSaveDto): Comment {

        val post =
            postRepository.findById(dto.postId).orElseThrow { throw EntityNotFoundException(dto.postId.toString()) }

        val user =
            userRepository.findUserById(dto.userId ?: 0)

        val comment =
            commentRepository.saveComment(dto.toEntity(post = post, user = user))

        dto.calculatePaddingLeft()
        commentRepository.saveCommentClosure(comment.id, dto.idAncestor)

        return comment
    }


    @Transactional
    fun deleteComment(id:Long) {

        val comment =
            commentRepository.findCommentById(id) ?: throw EntityNotFoundException("comment $id not found")

        comment.deleteByUser()


    }



    @Transactional(readOnly = true)
    fun findCommentByAncestorComment(idAncestor: Long) {

        val comments =
            commentRepository.findCommentByAncestorComment(idAncestor)

    }

    @Transactional(readOnly = true)
    fun findCommentsByBottomUp(commentId: Long): CommentCardDto {

        val commentClosures =
            commentRepository.findCommentClosuresByBottomUp(commentId, 2)

        return if (commentClosures.size == 1) {
            CommentCardDto.fromEntity(commentClosures.first())
        }else{
            commentClosures.map { CommentCardDto.fromEntity(it) }.first { it.idAncestor != it.idDescendant }
        }
    }


    @Transactional(readOnly = true)
    fun findCommentsByPostId(id: Long): List<CommentCardDto> {

        val commentClosures =
            commentRepository.findCommentsByPostId(id)

        return commentClosures.associateBy { it.idDescendant.id }.map { it.value }.toList().filter { it.depth == 0 }.map {
            createCommentTree(CommentCardDto.fromEntity(it), commentClosures.map { CommentCardDto.fromEntity(it) })
        }
    }


    private fun createCommentTree(parent: CommentCardDto, commentClosures: List<CommentCardDto>): CommentCardDto {

        for (commentClosure in commentClosures) {
            if (commentClosure.idAncestor == parent.commentId) {
                if (commentClosure.depth == 1) {
                    parent.childComments.add(commentClosure)
                    createCommentTree(commentClosure, commentClosures)
                }
            }
        }

        return parent
    }
}