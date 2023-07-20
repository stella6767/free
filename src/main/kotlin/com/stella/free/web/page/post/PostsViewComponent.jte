@import com.stella.free.core.blog.entity.Post
@import com.stella.free.web.component.blog.PostCardViewComponent
@import org.springframework.data.domain.Page

@param Page<Post> posts
@param PostCardViewComponent postCardViewComponent



@if(!posts.isLast())
<div
        class="container m-auto grid grid-cols-4 gap-4 "
        id="posts-container"
        hx-get="/blog/posts?page=${posts.getPageable().getPageNumber() + 1}"
        hx-trigger="revealed"
        hx-swap="afterend">
    @else
    <div
            class="container m-auto grid grid-cols-4 gap-4 "
            id="posts-container">

        @endif

        @for(Post post : posts.getContent())
            ${postCardViewComponent.render(post)}
        @endfor

        <%--    <div--%>
        <%--    ></div>--%>

    </div>

</div>