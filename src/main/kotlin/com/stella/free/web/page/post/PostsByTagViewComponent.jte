
@import com.stella.free.core.blog.entity.Post
@import com.stella.free.web.component.blog.post.PostCardViewComponent
@import org.springframework.data.domain.Page
@import org.springframework.util.StringUtils

@param Page<Post> posts
@param PostCardViewComponent postCardViewComponent
@param String tagName



@if(StringUtils.hasLength(tagName))
    <div class="container m-auto">
        <div class="flex justify-center">
            <h1 class="text-xl font-bold">"#${tagName}" 의 검색결과</h1>
        </div>
        <div class="flex justify-center">
            총  <span class="italic">${posts.getTotalElements()} </span> 개의 포스트를 찾았습니다.
        </div>
        <div class="flex justify-center mt-3">
            <button class="btn btn-wide" onclick="location.href='/blog'">전체보기</button>
        </div>
    </div>
@endif



@if(!posts.isLast())


    <div
            class="container m-auto"
            id="posts-tag-container"
            hx-get="/posts/tag?page=${posts.getPageable().getPageNumber() + 1}&tagName=${tagName}"
            hx-trigger="revealed"
            hx-swap="afterend">

        @for(Post post : posts.getContent())
            ${postCardViewComponent.render(post)}
        @endfor

    </div>
@else
    <div class="container m-auto"
         id="posts-tag-container">
        @for(Post post : posts.getContent())
            ${postCardViewComponent.render(post)}
        @endfor
    </div>
@endif
