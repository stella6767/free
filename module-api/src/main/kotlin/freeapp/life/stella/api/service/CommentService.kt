package freeapp.life.stella.api.service

import freeapp.life.stella.api.web.dto.CommentCardDto
import freeapp.life.stella.api.web.dto.CommentSaveDto
import freeapp.life.stella.storage.entity.Comment
import freeapp.life.stella.storage.repository.CommentRepository
import freeapp.life.stella.storage.repository.PostRepository
import freeapp.life.stella.storage.repository.UserRepository
import jakarta.persistence.EntityNotFoundException
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
) {

    private val log = KotlinLogging.logger {  }

    @Transactional
    fun saveComment(dto: CommentSaveDto): Comment {

        val post =
            postRepository.findById(dto.postId).orElseThrow { throw EntityNotFoundException(dto.postId.toString()) }

        val user =
            userRepository.findUserById(dto.userId ?: 0)

        val comment =
            commentRepository.saveComment(dto.toEntity(post = post, user = user))

        commentRepository.saveCommentClosure(comment.id, dto.idAncestor)

        return comment
    }


    @Transactional
    fun deleteComment(id: Long): Long {

        val comment =
            commentRepository.findCommentById(id) ?: throw EntityNotFoundException("comment $id not found")

        comment.deleteByUser()

        return comment.id
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
