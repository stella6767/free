@import com.stella.free.core.blog.dto.*
@import java.util.*

@param CommentCardDto comment
@param com.stella.free.web.component.blog.comment.CommentFormViewComponent commentFormViewComponent
@param com.stella.free.web.component.blog.comment.CommentCardViewComponent commentCardViewComponent
@param Integer paddingLeft
@param Long userId

<div  x-data="{ open: false, deleteOpen: false }" id="comment-card-${comment.getCommentId()}">

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
            <div class="mt-4 flex items-center justify-between">
                <div class="flex -space-x-2 mr-2">
                    <button class="text-right text-blue-500"
                            x-on:click="open = ! open"
                    >
                        Reply
                    </button>
                </div>


                <div>

                    @if(comment.getUserId() == 0)
                        <button class="btn btn-link delete-comment"
                                x-on:click="deleteOpen = ! deleteOpen"
                                x-show="!deleteOpen"
                        >X</button>
                    @elseif(comment.getUserId() == userId)
                        <button class="btn btn-link delete-comment"
                                x-on:click="deleteOpen = ! deleteOpen"
                                x-show="!deleteOpen"
                        >X</button>
                    @endif


                    <div x-show="deleteOpen" class="flex">
                        <!-- Content of the new div -->

                        !{var message = "제출";}

                        @if(userId != 0 &&  comment.getUserId() == userId)
                            !{message = "삭제";}
                        @else
                            <input type="text"
                                   placeholder="input password"
                                   name="password"
                                   class="input input-bordered input-xs w-full max-w-xs" />

                        @endif

                        <button class="btn btn-link delete-comment"
                                x-on:click="deleteOpen = ! deleteOpen"
                                hx-delete="/comment/${comment.getCommentId()}"
                                hx-include="[name='password']"
                                hx-swap="none"
                        >${message}</button>

                        <button class="btn btn-link delete-comment"
                                x-on:click="deleteOpen = ! deleteOpen"
                        >닫기</button>

                    </div>
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


