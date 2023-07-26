@import com.stella.free.core.blog.dto.PostCardDto
@import com.stella.free.core.blog.dto.PostDetailDto
@import com.stella.free.core.blog.entity.Post
@import java.util.*

@param PostCardDto post
@param int pageNum = 1



<div class="card w-80 bg-base-100 shadow-xl hover:bg-cyan-600 cursor-pointer"
     id="post-card-${post.getId()}" onclick="location.href='/post/${post.getId()}'">
    <figure class="bg-slate-300">
        <div x-data="{ loading: true }" >
            <img  x-show="loading"  alt="Result loading..." src="/img/spinner.svg"  />
            <img x-show="!loading"
                 src="${post.getThumbnail()}"
                 alt="post-thumbnail"
                 @load="loading = false"
            >
        </div>
    </figure>
    <div class="card-body h-32">
        <h2 class="card-title">${post.getTitle()}</h2>
        <p class="truncate">
            ${post.getThumbnailContent()}
        </p>
    </div>
</div>

