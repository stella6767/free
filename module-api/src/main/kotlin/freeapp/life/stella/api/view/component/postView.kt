package freeapp.life.stella.api.view.component

import freeapp.life.stella.api.dto.PostCardDto
import freeapp.life.stella.api.dto.PostDetailDto
import freeapp.life.stella.api.util.toJson
import kotlinx.html.*
import org.springframework.data.domain.Page
import org.springframework.util.StringUtils


fun DIV.postView(keyword: String, posts: Page<PostCardDto>) {


    div {
        id = "posts-container"
        classes = setOf("py-5")
        if (StringUtils.hasText(keyword)) {
            div {
                classes = setOf("container", "m-auto")
                div {
                    classes = setOf("flex", "justify-center")
                    h1 {
                        classes = setOf("text-xl", "font-bold")
                        +"${keyword}의 검색결과"
                    }
                }
                div {
                    classes = setOf("flex", "justify-center")
                    +"총"
                    span {
                        classes = setOf("italic")
                        +"${posts.totalElements}"
                    }
                    +"개의 포스트를 찾았습니다."
                }
                div {
                    classes = setOf("flex", "justify-center", "mt-3")
                    button {
                        classes = setOf("btn", "btn-wide")
                        attributes["onclick"] = "location.href='/blog'"
                        +"전체보기"
                    }
                }
            }
        }

        if (!posts.isLast) {

            div {
                classes = setOf("container", "m-auto", "grid", "grid-cols-4", "gap-4")
                //id = "posts-container"
                attributes["hx-get"] = "/posts?page=${posts.pageable.pageNumber + 1}&keyword=${keyword}"
                attributes["hx-trigger"] = "revealed"
                attributes["hx-swap"] = "afterend"

                for (post in posts.content) {
                    postCardView(post)
                }
            }

        } else {
            div {
                classes = setOf("container", "m-auto", "grid", "grid-cols-4", "gap-4")
                //id = "posts-container"
                for (post in posts.content) {
                    postCardView(post)
                }
            }
        }
    }
}


fun DIV.postCardView(post: PostCardDto) {

    div {
        classes = setOf("card", "mt-5", "bg-base-100", "shadow-xl", "hover:bg-cyan-600", "cursor-pointer")
        id = "post-card-${post.id}"

//        attributes["hx-trigger"] = "click"
//        attributes["hx-get"] = "/post/${post.id}"
//        attributes["hx-swap"] = "innerHTML"
//        attributes["hx-target"] = "#content-body"

        onClick = "location.href='/page/post/${post.id}'"

        figure {
            classes = setOf("bg-slate-300")
            div {

                img {
                    //attributes["x-show"] = "!loading"
                    src = "${post.thumbnail}"
                    alt = "post-thumbnail"
                    attributes["x-on-load"] = "loading = false"
                }
            }
        }
        div {
            classes = setOf("card-body", "h-32")
            h2 {
                classes = setOf("card-title")
                +"${post.title}"
            }
            p {
                classes = setOf("truncate")
                +"${post.thumbnailContent}"
            }
        }
    }
}


fun DIV.postDetailView(
    userId: Long?,
    post: PostDetailDto,
) {

    link {
        attributes["rel"] = "stylesheet"
        href = "https://uicdn.toast.com/editor/latest/toastui-editor.min.css"
    }

    div {
        id = "post-detail"
        classes = setOf("w-3/6", "mx-auto", "mb-5", "py-5")
        div {
            classes = setOf(
                "sm:text-3xl",
                "md:text-3xl",
                "lg:text-3xl",
                "text-black",
                "xl:text-4xl",
                "font-bold",
                "cursor-pointer"
            )
            +"${post.title}"
        }
        div {
            classes = setOf("font-light", "text-gray-600", "mt-2", "flex", "justify-between")
            h1 {
                classes = setOf("font-bold", "text-gray-700", "hover:underline")
                +"By ${post.username} · ${post.createdAt}"
            }

            if (userId != null && (post.userId == userId)) {

                div {
                    classes = setOf("flex", "gap-x-1")
                    div {
                        attributes["hx-get"] = "/post/editor?postId=${post.id}"
                        attributes["hx-swap"] = "outerHTML"
                        attributes["hx-target"] = "#post-detail"
                        classes = setOf("btn")
                        +"수정"
                    }
                    div {
                        button {
                            classes = setOf("btn")
                            attributes["onclick"] = "post_delete_modal.showModal()"
                            +"삭제"
                        }
                    }
                }
            }

        }
        div {
            classes = setOf("mt-2")
            for (postTag in post.postTags) {
                tagView(postTag)
            }
        }

        postModalView(post)
        htmlViewer(post.content, false)
    }

    commentSectionView(post, userId)
}


fun DIV.postEditorView(post: PostDetailDto?) {

    link {
        attributes["rel"] = "stylesheet"
        href = "https://uicdn.toast.com/editor/latest/toastui-editor.min.css"
    }
    script {
        src = "https://cdn.jsdelivr.net/npm/@yaireo/tagify"
    }
    link {
        href = "https://cdn.jsdelivr.net/npm/@yaireo/tagify/dist/tagify.css"
        attributes["rel"] = "stylesheet"
        type = "text/css"
    }

    form {
        id = "post-form"
        classes = setOf("py-4", "flex", "flex-col", "gap-y-3", "mb-3")
        h1 {
            classes = setOf(
                "text-center",
                "text-black",
                "mb-4",
                "text-4xl",
                "font-extrabold",
                "leading-none",
                "tracking-tight",
                "md:text-5xl",
                "lg:text-6xl",
            )
            +"TOAST UI Editor"
        }
        div {
            classes = setOf("flex", "justify-center", "gap-x-3", "w-6/12", "mx-auto")
            input {
                type = InputType.text
                value = "${post?.username ?: ""}"
                attributes["name"] = "username"
                attributes["placeholder"] = "input your username. if you do not input, default name is Anonymous"
                classes = setOf("input", "input-bordered", "w-full", "justify-self-center", "")
            }
        }
        div {
            classes = setOf("flex", "w-6/12", "mx-auto", "justify-center")
            input {
                type = InputType.text
                required = true
                value = "${post?.title ?: ""}"
                attributes["name"] = "title"
                attributes["placeholder"] = "input your title"
                classes = setOf("input", "input-bordered", "justify-self-center", "w-full")
            }
        }
        div {
            classes = setOf("flex", "w-6/12", "mx-auto", "justify-center")
            input {
                attributes["placeholder"] = "태그를 입력하고 Enter 누르면 됩니다."
                attributes["name"] = "hashtag"
                attributes["tabindex"] = "2"
                classes = setOf("input", "input-bordered", "justify-self-center", "w-full", "text-white")
                attributes["value"] = ""
                type = InputType.text
            }
        }
        div {
            id = "editor"
            classes = setOf("border-2", "w-1/2", "mx-auto")
        }
        br {
        }
        div {
            classes = setOf("flex", "justify-center", "gap-x-2")
            button {
                id = "post-submit-btn"
                classes = setOf("btn", "btn-success")
                +"제출"
            }
            button {
                attributes["hx-on-click"] = "cancelPost()"
                classes = setOf("btn", "btn-warning")
                +"취소"
            }
        }

        input {
            type = InputType.hidden
            value = "${post?.id ?: 0}"
            name = "postId"
        }

        input {
            type = InputType.hidden
            value = "${post?.postTags?.toJson()}"
            name = "originTags"
        }

    }

    script {
        src = "https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"
    }

    script {
        unsafe {
            raw(
                """                                    
                    var contentValue = '${post?.content ?: "please input content"}';                
            """.trimIndent()
            )
        }
    }

    script {
        src = "/js/post.js"
        defer = true
    }

}


fun DIV.postModalView(post: PostDetailDto) {

    dialog {
        id = "post_delete_modal"
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
                            attributes["hx-delete"] = "/post/${post.id}"
                            attributes["hx-swap"] = "none"
                            attributes["hx-on--after-request"] = "location.href='/blog'"
                            classes = setOf("btn", "")
                            id = "post-delete-btn"
                            +"삭제"
                        }
                    }
                }
            }
            div {
                classes = setOf("modal-action")
                button {
                    classes = setOf("btn")
                    +"Close"
                }
            }
        }
    }
}





fun DIV.postByTagView(
    tagName: String,
    posts: Page<PostCardDto>
) {

    if (StringUtils.hasText(tagName)) {

        div {
            classes = setOf("container", "m-auto" , "py-5")
            div {
                classes = setOf("flex", "justify-center")
                h1 {
                    classes = setOf("text-xl", "font-bold", "text-black")
                    +"${tagName} 의 검색결과"
                }
            }
            div {
                classes = setOf("flex", "justify-center", "text-black")
                +"총"
                span {
                    classes = setOf("italic", "text-black")
                    +"${posts.totalElements}"
                }
                +"개의 포스트를 찾았습니다."
            }
            div {
                classes = setOf("flex", "justify-center", "mt-3")
                button {
                    classes = setOf("btn", "btn-wide")
                    attributes["onclick"] = "location.href='/blog'"
                    +"전체보기"
                }
            }
        }
    }

    if (!posts.isLast) {
        div {
            classes = setOf("container", "m-auto")
            id = "posts-tag-container"
            attributes["hx-get"] = "/posts/tag?page=${posts.pageable.pageNumber + 1}&tagName=${tagName}"
            attributes["hx-trigger"] = "revealed"
            attributes["hx-swap"] = "afterend"
            for (post in posts.content) {
                postCardView(post)
            }
        }
    } else {
        div {
            classes = setOf("container", "m-auto")
            id = "posts-tag-container"
            for (post in posts.content) {
                postCardView(post)
            }
        }
    }


}