package freeapp.life.stella.api.view.component

import freeapp.life.stella.api.dto.CommentCardDto
import freeapp.life.stella.api.dto.PostDetailDto
import kotlinx.html.*

//todo post 글 마크다운으로 변환, 알파인 js 제거 및, loading image

fun DIV.commentSectionView(
    //comments: List<CommentCardDto>,
    post: PostDetailDto,
    userId: Long?
) {

    div {
        classes = setOf("antialiased", "mx-auto", "w-3/6", "mt-10")
        h3 {
            classes = setOf("mb-4", "text-lg", "font-semibold", "text-gray-900")
            +"Comments"
        }

        commentFormView(userId, post.id, 0)

        div {
            classes = setOf("space-y-4", "mt-3")
            id = "comment-card-container"

            for (comment in post.comments) {
                commentCardView(comment, userId)
            }
        }
    }

}


fun DIV.commentCardView(
    comment: CommentCardDto,
    userId: Long?,
) {

    div {
        id = "comment-card-${comment.commentId}"
        div {
            classes = setOf("flex", "mt-3")
            id = "comment-card-${comment.commentId}"
            div {
                classes = setOf("text-black", "font-bold")
            }
            div {
                classes =
                    setOf("flex-1", "border", "rounded-lg", "px-4", "py-2", "sm:px-6", "sm:py-4", "leading-relaxed")
                strong {
                    +"${comment.username}"
                }
                span {
                    classes = setOf("text-xs", "text-black")
                    +"${comment.createdAt}"
                }
                p {
                    classes = setOf("text-sm", "text-black")
                    id = "comment-content-${comment.commentId}"
                    if (comment.parentCommentUsername.isNotEmpty()) {
                        span {
                            classes = setOf("truncate", "text-sm", "text-gray")
                            +"@${comment.parentCommentUsername}"
                        }
                    }
                    +"${comment.content}"
                }
                div {
                    classes = setOf("mt-4", "flex", "items-center", "justify-between")
                    div {
                        classes = setOf("flex", "-space-x-2", "mr-2")
                        button {
                            classes = setOf("text-right", "text-blue-500")
                            attributes["hx-on:click"] =
                                "htmx.toggleClass('#comment-reply-form-${comment.commentId}', 'hidden')"
                            +"Reply"
                        }

                    }
                    div {
                        if (comment.userId == userId) {
                            button {
                                classes = setOf("btn", "btn-link", "delete-comment")
                                attributes["onclick"] = "comment_delete_modal.showModal()"
                                +"X"
                            }
                        }

                        commentDeleteModal(userId, comment)
                    }
                }
            }
        }

        div("hidden") {
            id = "comment-reply-form-${comment.commentId}"
            commentFormView(userId, comment.postId)
        }

    }
}



private fun DIV.commentDeleteModal(userId: Long?, comment: CommentCardDto) {

    dialog {
        id = "comment_delete_modal"
        classes = setOf("modal")
        form {
            attributes["method"] = "dialog"
            classes = setOf("modal-box")
            div {
                classes = setOf("bg-white", "shadow", "w-full", "rounded-lg", "divide-y", "divide-gray-200")
                div {
                    classes = setOf("p-5")
                    div {
                        classes = setOf("mt-3", "space-x-2", "flex", "justify-around")
                        div {
                            attributes["hx-delete"] = "/comment/${comment.commentId}"
                            attributes["hx-swap"] = "none"
                            attributes["hx-include"] = "#commentPassword-${comment.commentId}"
                            attributes["hx-trigger"] = "click"
                            attributes["hx-target"] = "#comment-content-${comment.commentId}"
                            //attributes["onclick"] = "comment_delete_modal.close()"
                            classes = setOf("btn", "modal-action")
                            id = "comment-delete-btn"
                            +"정말로 삭제하시겠습니까?"
                        }
                    }
                }
            }
//            div {
//                classes = setOf("modal-action")
//                button {
//                    classes = setOf("btn")
//                    +"Close"
//                }
//            }
        }
    }



//    div {
//        classes = setOf("flex")
//        if (userId != 0L && comment.userId == userId) {
//            input {
//                type = InputType.hidden
//                id = "commentPassword-${comment.commentId}"
//                attributes["name"] = "test"
//                attributes["value"] = ""
//            }
//        } else {
//            input {
//                type = InputType.text
//                attributes["placeholder"] = "input password"
//                attributes["name"] = "commentPassword"
//                id = "commentPassword-${comment.commentId}"
//                attributes["required"] = ""
//                classes = setOf("input", "input-bordered", "input-xs", "w-full", "max-w-xs")
//            }
//        }
//
//        button {
//            classes = setOf("btn", "btn-link", "delete-comment")
//            attributes["x-on:click"] = "deleteOpen = ! deleteOpen"
//            attributes["hx-delete"] = "/comment/${comment.commentId}"
//            attributes["hx-include"] = "#commentPassword-${comment.commentId}"
//            attributes["hx-trigger"] = "click"
//            attributes["hx-target"] = "#comment-content-${comment.commentId}"
//            +"제출"
//        }
//
//        button {
//            classes = setOf("btn", "btn-link", "delete-comment")
//            attributes["x-on:click"] = "deleteOpen = ! deleteOpen"
//            +"닫기"
//        }
//    }
}


fun DIV.commentFormView(
    userId: Long?,
    postId: Long,
    idAncestor: Long = 0,
) {


    form {
        classes = setOf("bg-white", "rounded-lg", "border", "p-2", "mx-auto", "")
        id = "comment-form-${idAncestor}"
        attributes["hx-post"] = "/comment"
        attributes["hx-swap"] = "beforeend"
        if (idAncestor != 0L) {
            attributes["hx-target"] = "#comment-card-${idAncestor}"
        } else {
            attributes["hx-target"] = "#comment-card-container"
        }
        attributes["hx-on"] = "htmx:afterRequest: document.getElementById('comment-form-${idAncestor}').reset()"



        div {
            classes = setOf("px-3", "mb-2", "mt-2")
            input {
                type = InputType.text
                required = true
                attributes["name"] = "nickName"
                attributes["placeholder"] = "input your nickname"
                classes = setOf("input", "input-bordered")
            }

            if (userId == null) {
                input {
                    type = InputType.text
                    required = true
                    attributes["name"] = "password"
                    attributes["placeholder"] = "input your password"
                    classes = setOf("input", "input-bordered", "ml-3")
                }
            } else {
                input {
                    type = InputType.hidden
                    attributes["name"] = "userId"
                    attributes["value"] = "$userId"
                }
            }
        }


        input {
            type = InputType.hidden
            attributes["name"] = "postId"
            attributes["value"] = "$postId"
        }

        div {
            classes = setOf("px-3", "mb-2", "mt-2")
            textArea {
                attributes["placeholder"] = "comment"
                attributes["name"] = "content"
                id = "comment-text"
                classes = setOf(
                    "w-full",
                    "bg-gray-100",
                    "rounded",
                    "border",
                    "border-gray-400",
                    "leading-normal",
                    "resize-none",
                    "h-20",
                    "py-2",
                    "px-3",
                    "font-medium",
                    "placeholder-gray-700",
                    "focus:outline-none",
                    "focus:bg-white",
                    "text-black"
                )
            }
        }
        div {
            classes = setOf("flex", "justify-end", "px-4")
            input {
                type = InputType.submit
                classes =
                    setOf("px-2.5", "py-1.5", "rounded-md", "text-white", "text-sm", "bg-indigo-500", "cursor-pointer")
                //attributes["x-on:click"] = "open = ! open"
            }
        }
    }


}


