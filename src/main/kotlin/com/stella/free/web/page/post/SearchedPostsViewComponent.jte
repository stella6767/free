@import com.stella.free.core.blog.entity.Post
@import com.stella.free.web.component.blog.post.PostCardViewComponent
@import org.springframework.data.domain.Page

@param Page<Post> posts
@param PostCardViewComponent postCardViewComponent
@param String keyword


<div class="">
    <div class="">
        <svg width="17" height="17" viewBox="0 0 17 17">
            <path fill-rule="evenodd" d="M13.66 7.36a6.3 6.3 0 1 1-12.598 0 6.3 6.3 0 0 1 12.598 0zm-1.73 5.772a7.36 7.36 0 1 1 1.201-1.201l3.636 3.635c.31.31.31.815 0 1.126l-.075.075a.796.796 0 0 1-1.126 0l-3.636-3.635z" clip-rule="evenodd" fill="currentColor"></path>
        </svg>
        </div>
</div>

    @if(!posts.isLast())
        <div
                class="container mx-auto w-1/2"
                id="posts-searched-container"
                hx-get="/posts?keyword=${keyword}&page=${posts.getPageable().getPageNumber() + 1}"
                hx-trigger="revealed"
                hx-swap="afterend">

            @for(Post post : posts.getContent())
                ${postCardViewComponent.render(post)}
            @endfor
        </div>
    @else
        <div
                class="container mx-auto w-1/2"
                id="posts-searched-container">
            @for(Post post : posts.getContent())
                ${postCardViewComponent.render(post)}
            @endfor
        </div>
    @endif


