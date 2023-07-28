@import com.stella.free.core.blog.dto.*
@import java.util.*

@param CommentCardDto comment
@param com.stella.free.web.component.blog.comment.CommentFormViewComponent commentFormViewComponent
@param com.stella.free.web.component.blog.comment.CommentReplyViewComponent commentReplyViewComponent


<div  x-data="{ open: false }" id="comment-card-${comment.getCommentId()}">

    <div class="flex" id="comment-card-${comment.getCommentId()}" >
        <div class="flex-1 border rounded-lg px-4 py-2 sm:px-6 sm:py-4 leading-relaxed">
            <strong>${comment.getUsername()}</strong>
            <span class="text-xs text-gray-400">${comment.getCreatedAt()}</span>
            <p class="text-sm">
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
        ${commentFormViewComponent.render(comment.getPostId(), comment.getCommentId())}
    </div>


    @for(CommentCardDto childComment : comment.getChildComments())
        ${commentReplyViewComponent.render(commentReplyViewComponent, childComment, comment.getUsername(), 3)}
    @endfor


</div>


<%--<div class="flex">--%>
<%--    <div class="flex-shrink-0 mr-3">--%>
<%--        <img class="mt-2 rounded-full w-8 h-8 sm:w-10 sm:h-10"--%>
<%--             src="https://images.unsplash.com/photo-1604426633861-11b2faead63c?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=200&h=200&q=80"--%>
<%--             alt="">--%>
<%--    </div>--%>
<%--    <div class="flex-1 border rounded-lg px-4 py-2 sm:px-6 sm:py-4 leading-relaxed">--%>
<%--        <strong>Sarah</strong> <span class="text-xs text-gray-400">3:34 PM</span>--%>
<%--        <p class="text-sm">--%>
<%--            Lorem ipsum dolor sit amet, consetetur sadipscing elitr,--%>
<%--            sed diam nonumy eirmod tempor invidunt ut labore et dolore--%>
<%--            magna aliquyam erat, sed diam voluptua.--%>
<%--        </p>--%>

<%--        <h4 class="my-5 uppercase tracking-wide text-gray-400 font-bold text-xs">Replies</h4>--%>

<%--        <div class="space-y-4">--%>
<%--            <div class="flex">--%>
<%--                <div class="flex-shrink-0 mr-3">--%>
<%--                    <img class="mt-3 rounded-full w-6 h-6 sm:w-8 sm:h-8"--%>
<%--                         src="https://images.unsplash.com/photo-1604426633861-11b2faead63c?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=200&h=200&q=80"--%>
<%--                         alt="">--%>
<%--                </div>--%>
<%--                <div class="flex-1 bg-gray-100 rounded-lg px-4 py-2 sm:px-6 sm:py-4 leading-relaxed">--%>
<%--                    <strong>Sarah</strong> <span class="text-xs text-gray-400">3:34 PM</span>--%>
<%--                    <p class="text-xs sm:text-sm">--%>
<%--                        Lorem ipsum dolor sit amet, consetetur sadipscing elitr,--%>
<%--                        sed diam nonumy eirmod tempor invidunt ut labore et dolore--%>
<%--                        magna aliquyam erat, sed diam voluptua.--%>
<%--                    </p>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--            <div class="flex">--%>
<%--                <div class="flex-shrink-0 mr-3">--%>
<%--                    <img class="mt-3 rounded-full w-6 h-6 sm:w-8 sm:h-8"--%>
<%--                         src="https://images.unsplash.com/photo-1604426633861-11b2faead63c?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=200&h=200&q=80"--%>
<%--                         alt="">--%>
<%--                </div>--%>
<%--                <div class="flex-1 bg-gray-100 rounded-lg px-4 py-2 sm:px-6 sm:py-4 leading-relaxed">--%>
<%--                    <strong>Sarah</strong> <span class="text-xs text-gray-400">3:34 PM</span>--%>
<%--                    <p class="text-xs sm:text-sm">--%>
<%--                        Lorem ipsum dolor sit amet, consetetur sadipscing elitr,--%>
<%--                        sed diam nonumy eirmod tempor invidunt ut labore et dolore--%>
<%--                        magna aliquyam erat, sed diam voluptua.--%>
<%--                    </p>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</div>--%>
