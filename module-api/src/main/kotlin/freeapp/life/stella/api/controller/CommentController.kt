package freeapp.life.stella.api.controller

import freeapp.life.stella.api.config.security.UserPrincipal
import freeapp.life.stella.api.dto.CommentSaveDto
import freeapp.life.stella.api.service.CommentService
import freeapp.life.stella.api.view.component.commentCardView
import freeapp.life.stella.api.view.component.postEditorView
import freeapp.life.stella.api.view.page.renderComponent
import mu.KotlinLogging
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*


@RestController
class CommentController(
    private val commentService: CommentService,
) {

    private val log = KotlinLogging.logger {  }


    @DeleteMapping("/comment/{id}")
    fun deleteCommentById(
        @PathVariable id:Long,
    ): Long {

        return commentService.deleteComment(id)
    }

    @PostMapping("/comment")
    fun saveComment(
        commentSaveDto: CommentSaveDto,
        @AuthenticationPrincipal userPrincipal: UserPrincipal?
    ) : String{

        val comment =
            commentService.saveComment(commentSaveDto)

        val childComment =
            commentService.findCommentsByBottomUp(comment.id)

        return renderComponent {
            commentCardView(childComment, userPrincipal?.user?.id)
        }
    }


//    @GetMapping("/comment/form/{postId}")
//    fun getCommentForm(@PathVariable postId: Long): ViewContext {
//        return commentFormViewComponent.render(postId)
//    }



}