package com.stella.free.core.blog.service

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
    fun findCommentByAncestorComment(idAncestor:Long) {

        commentRepository.findCommentByAncestorComment(idAncestor)
            .map { it }

    }


    @Transactional(readOnly = true)
    fun findCommentsByPostId(id:Long): List<Comment> {
        return commentRepository.findCommentsByPostId(id)
    }




}