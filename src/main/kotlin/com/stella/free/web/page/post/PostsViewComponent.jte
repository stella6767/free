@import com.stella.free.core.blog.entity.Post
@import com.stella.free.web.component.blog.post.PostCardViewComponent
@import org.springframework.data.domain.Page
@import org.springframework.util.StringUtils

@param Page<Post> posts
@param PostCardViewComponent postCardViewComponent
@param String keyword = ""






@if(StringUtils.hasLength(keyword))
    <div class="container m-auto">
        <div class="flex justify-center">
            <h1 class="text-xl font-bold">"${keyword}" 의 검색결과</h1>
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
            class="container m-auto grid grid-cols-4 gap-4"
            id="posts-container"
            hx-get="/posts?page=${posts.getPageable().getPageNumber() + 1}&keyword=${keyword}"
            hx-trigger="revealed"
            hx-swap="afterend">

        @for(Post post : posts.getContent())
            ${postCardViewComponent.render(post)}
        @endfor

    </div>
@else
    <div class="container m-auto grid grid-cols-4 gap-4"
         id="posts-container">
        @for(Post post : posts.getContent())
            ${postCardViewComponent.render(post)}
        @endfor
    </div>
@endif


<script>
    
    <%--if ('${keyword}' === '') {--%>
    <%--    console.log("why??")--%>
    <%--    document.querySelector('#posts-container').classList.add('grid', 'grid-cols-4', 'gap-4')--%>
    <%--}--%>


</script>


