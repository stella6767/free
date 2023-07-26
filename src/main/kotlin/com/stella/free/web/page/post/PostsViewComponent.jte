@import com.stella.free.core.blog.entity.Post
@import com.stella.free.web.component.blog.post.PostCardViewComponent
@import org.springframework.data.domain.Page

@param Page<Post> posts
@param PostCardViewComponent postCardViewComponent



@if(!posts.isLast())
    <div
            class="container m-auto grid grid-cols-4 gap-4 "
            id="posts-container"
            hx-get="/posts?page=${posts.getPageable().getPageNumber() + 1}"
            hx-trigger="revealed"ㅖ
            hx-swap="afterend">

        @for(Post post : posts.getContent())
            ${postCardViewComponent.render(post)}
        @endfor
    </div>
@else
    <div
            class="container m-auto grid grid-cols-4 gap-4 "
            id="posts-container">
        @for(Post post : posts.getContent())
            ${postCardViewComponent.render(post)}
        @endfor
    </div>
@endif





