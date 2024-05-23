package freeapp.life.stella.api.view.component

import freeapp.life.stella.api.dto.PostCardDto
import freeapp.life.stella.storage.entity.Post
import freeapp.life.stella.storage.entity.Todo
import kotlinx.html.*
import org.springframework.data.domain.Page
import org.springframework.util.StringUtils


fun DIV.postView(keyword: String, posts: Page<PostCardDto>) {

    if (StringUtils.hasText(keyword)) {
        div {
            classes = setOf("container", "m-auto")
            div {
                classes = setOf("flex", "justify-center")
                h1 {
                    classes = setOf("text-xl", "font-bold")
                    +"${ keyword }의 검색결과"
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
            id = "posts-container"
            attributes["hx-get"] = "/posts?page=${posts.pageable.pageNumber + 1}&keyword=${keyword}"
            attributes["hx-trigger"] = "revealed"
            attributes["hx-swap"] = "afterend"

            for (post in posts.content) {
                postCardView(post)
            }
        }

    }else{
        div {
            classes = setOf("container", "m-auto", "grid", "grid-cols-4", "gap-4")
            id = "posts-container"
            for (post in posts.content) {
                postCardView(post)
            }
        }
    }

}


fun DIV.postCardView(post:PostCardDto) {

    div {
        classes = setOf("card", "mt-5", "bg-base-100", "shadow-xl", "hover:bg-cyan-600", "cursor-pointer")
        id = "post-card-${post.id}"
        attributes["onclick"] = "location.href='/post/${post.id}'"
        figure {
            classes = setOf("bg-slate-300")
            div {
                attributes["x-data"] = "{ loading: true }"
                img {
                    attributes["x-show"] = "loading"
                    alt = "Result loading..."
                    src = "/img/spinner.svg"
                }
                img {
                    attributes["x-show"] = "!loading"
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
