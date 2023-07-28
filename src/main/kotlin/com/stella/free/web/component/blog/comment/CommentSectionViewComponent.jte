@import com.stella.free.core.blog.dto.CommentCardDto
@import com.stella.free.core.blog.entity.Comment
@import java.util.*

@param com.stella.free.web.component.blog.comment.CommentFormViewComponent commentFormViewComponent
@param com.stella.free.web.component.blog.comment.CommentCardViewComponent commentCardViewComponent
@param com.stella.free.web.component.blog.comment.CommentReplyViewComponent commentReplyViewComponent
@param com.stella.free.core.blog.dto.PostDetailDto post
@param List<CommentCardDto> comments


<div class="antialiased mx-auto w-3/6 mt-20" >
    <h3 class="mb-4 text-lg font-semibold text-gray-900">Comments</h3>
    ${commentFormViewComponent.render(post.getId(), 0)}
    <div class="space-y-4 mt-3" id="comment-card-container" >

        @for(CommentCardDto comment : comments)
            ${commentCardViewComponent.render(comment)}
        @endfor

    </div>
</div>