@import com.stella.free.core.blog.entity.Post
@import com.stella.free.web.component.blog.post.PostCardViewComponent
@import org.springframework.data.domain.Page
@import org.springframework.util.StringUtils

@param Page<Post> posts
@param PostCardViewComponent postCardViewComponent
@param String keyword = ""



@if(!posts.isLast())


    @if(StringUtils.hasLength(keyword))



        
    @endif
    
    <div
            class="container m-auto"
            id="posts-container"
            hx-get="/posts?page=${posts.getPageable().getPageNumber() + 1}&keyword=${keyword}"
            hx-trigger="revealed"
            hx-swap="afterend">

        @for(Post post : posts.getContent())
            ${postCardViewComponent.render(post)}
        @endfor

    </div>
@else
    <div class="container m-auto"
         id="posts-container">
        @for(Post post : posts.getContent())
            ${postCardViewComponent.render(post)}
        @endfor
    </div>
@endif


<script>
    
    if ('${keyword}' === '') {
        console.log("why??")
        document.querySelector('#posts-container').classList.add('grid', 'grid-cols-4', 'gap-4')
    }


</script>


