@import com.stella.free.entity.Post
@import java.util.*

@param Post post



<div class="card card-compact w-96 bg-base-100 shadow-xl">
    <figure><img src="${post.getThumbnail()}" alt="Shoes" /></figure>
    <div class="card-body">
        <h2 class="card-title">${post.getTitle()}</h2>
        <p>${post.getContent()}</p>
        <div class="card-actions justify-end">
            <button class="btn btn-primary">상세 보기</button>
        </div>
    </div>
</div>