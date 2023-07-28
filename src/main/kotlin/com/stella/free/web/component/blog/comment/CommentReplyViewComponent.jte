@import com.stella.free.core.blog.dto.*

@param com.stella.free.web.component.blog.comment.CommentReplyViewComponent commentReplyViewComponent
@param com.stella.free.web.component.blog.comment.CommentFormViewComponent commentFormViewComponent
@param CommentCardDto childComment
@param String parentCommentUsername
@param Integer paddingLeft


<div x-data="{ open: false }" id="comment-card-${childComment.getCommentId()}" >
    <div class="flex mt-3">
        <div class="text-gray-300 font-bold pl-${paddingLeft}"></div>
        <div class="flex-1 border rounded-lg px-4 py-2 sm:px-6 sm:py-4 leading-relaxed">
            <strong>${childComment.getUsername()}</strong>
            <span class="text-xs text-gray-400">${childComment.getCreatedAt()}</span>
            <p class="text-sm">
                <span class="truncate text-sm text-gray-400">@${parentCommentUsername} </span>
                ${childComment.getContent()}
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

    <div id="comment-reply-form-${childComment.getCommentId()}" x-show="open">
        ${commentFormViewComponent.render(childComment.getPostId(), childComment.getCommentId())}
    </div>


    @for(CommentCardDto childChildComment : childComment.getChildComments())
        ${commentReplyViewComponent.render(
        commentReplyViewComponent, childChildComment, childComment.getUsername(), paddingLeft * 2
        )}
    @endfor
</div>


