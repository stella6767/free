@import com.stella.free.core.blog.dto.*
@import java.util.*

@param CommentCardDto comment
@param com.stella.free.web.component.blog.comment.CommentFormViewComponent commentFormViewComponent
@param com.stella.free.web.component.blog.comment.CommentCardViewComponent commentCardViewComponent
@param Integer paddingLeft

<div  x-data="{ open: false }" id="comment-card-${comment.getCommentId()}">

    <div class="flex mt-3" id="comment-card-${comment.getCommentId()}" >
        <div class="text-gray-300 font-bold pl-${paddingLeft}"></div>
        <div class="flex-1 border rounded-lg px-4 py-2 sm:px-6 sm:py-4 leading-relaxed">
            <strong>${comment.getUsername()}</strong>
            <span class="text-xs text-gray-400">${comment.getCreatedAt()}</span>
            <p class="text-sm">
                @if(comment.getParentCommentUsername().isEmpty())
                @else
                    <span class="truncate text-sm text-gray-400">@${comment.getParentCommentUsername()}</span>
                @endif
                ${comment.getContent()}
            </p>
            <div class="mt-4 flex items-center">
                <div class="flex -space-x-2 mr-2">
                    <button class="text-right text-blue-500"
                            x-on:click="open = ! open"
                    >
                        Reply
                    </button>
                </div>
            </div>
        </div>


    </div>

    <div id="comment-reply-form-${comment.getCommentId()}" x-show="open">
        ${commentFormViewComponent.render(comment.getPostId(), comment.getCommentId(), paddingLeft)}
    </div>


    @for(CommentCardDto childComment : comment.getChildComments())
        ${commentCardViewComponent.render(childComment, commentCardViewComponent, paddingLeft + 3)}
    @endfor


</div>


