package com.stella.free.core.blog.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.stella.free.core.account.repo.UserRepository
import com.stella.free.core.blog.dto.CommentCardDto
import com.stella.free.core.blog.dto.CommentSaveDto
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
    fun saveComment(dto: CommentSaveDto): CommentCardDto {

        log.info(dto.toString())
        val post =
            postRepository.findById(dto.postId).orElseThrow { throw EntityNotFoundException(dto.postId.toString()) }
        val user =
            userRepository.getReferenceById(dto.userId)
        val comment =
            commentRepository.saveComment(dto.toEntity(post = post, user = user))

        commentRepository.saveCommentClosure(comment.id, dto.idAncestor)

        return comment.toCardDto()
    }


    @Transactional(readOnly = true)
    fun findCommentByAncestorComment(idAncestor: Long) {

        commentRepository.findCommentByAncestorComment(idAncestor)
            .map { it }

    }


    @Transactional(readOnly = true)
    fun findCommentsByPostId(id: Long): List<CommentCardDto> {

        val commentClosures =
            commentRepository.findCommentsByPostId(99)

        return commentClosures.associateBy { it.idDescendant.id }.map { it.value }.toList().filter { it.depth == 0 }.map {
            createCommentTree(it.toCardDto(), commentClosures.map { it.toCardDto() })
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