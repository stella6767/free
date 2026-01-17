package freeapp.life.stella.api.web.blog

import freeapp.life.stella.api.config.security.UserPrincipal
import freeapp.life.stella.api.service.CommentService
import freeapp.life.stella.api.web.dto.CommentSaveDto
import mu.KotlinLogging
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class CommentController(
    private val commentService: CommentService,
) {

    private val log = KotlinLogging.logger {  }


    @ResponseBody
    @DeleteMapping("/comment/{id}")
    fun deleteCommentById(
        @PathVariable id:Long,
    ) {

        return commentService.deleteComment(id)
    }

    @PostMapping("/comment")
    fun saveComment(
        model: Model,
        commentSaveDto: CommentSaveDto,
        @AuthenticationPrincipal userPrincipal: UserPrincipal?
    ) : String{

        val comment =
            commentService.saveComment(commentSaveDto)

        val childComment =
            commentService.findCommentsByBottomUp(comment.id)

        model.addAttribute("comment", childComment)
        model.addAttribute("userId", userPrincipal?.user?.id ?: 0)

        return "component/comment/commentCardView"
    }


}