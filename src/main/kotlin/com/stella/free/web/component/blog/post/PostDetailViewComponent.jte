@import gg.jte.Content

@param com.stella.free.core.blog.dto.PostDetailDto post
@param com.stella.free.web.component.blog.comment.CommentSectionViewComponent commentSectionViewComponent

@param java.util.List<String> postTags
@param com.stella.free.web.component.blog.tag.TagViewComponent tagViewComponent
@param com.stella.free.web.component.blog.post.PostModalViewComponent postModalViewComponent
@param Content content

<link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css"/>
<%--<script src="https://cdn.jsdelivr.net/npm/@yaireo/tagify"></script>--%>
<%--<link href="https://cdn.jsdelivr.net/npm/@yaireo/tagify/dist/tagify.css" rel="stylesheet" type="text/css" />--%>

<div class="w-3/6 mx-auto mb-5" >

    <div class="sm:text-3xl md:text-3xl lg:text-3xl xl:text-4xl font-bold cursor-pointer">
        ${post.getTitle()}
    </div>


    <div class="font-light text-gray-600 mt-2 flex justify-between">
        <h1 class="font-bold text-gray-700 hover:underline">
            By ${post.getUsername()} · ${post.getCreatedAt()}
        </h1>

<%--        post.getDeletedAt() == null--%>

        @if(post.getDeletedAt() == null)
            <div class="flex gap-x-1">
                <div
                        onclick="location.href='/post/editor?postId=${post.getId()}'"
                        class="btn">수정
                </div>
                <div>
                    <button class="btn" onclick="post_delete_modal.showModal()">삭제</button>
                </div>
            </div>
        @endif


    </div>

    <div class="mt-2">

        @for(String tag : postTags)
            ${tagViewComponent.render(tag)}
        @endfor

    </div>

    ${postModalViewComponent.render(post)}

</div>


<div id="viewer"></div>

${commentSectionViewComponent.render(post)}

<%--<span onclick="showContent('${post.getContent()}')">Click me</span>--%>




<style>


    #viewer {
        width: 50%;
        margin: 0 auto;
    }

</style>

<script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>
<%--<script type="module" src="https://md-block.verou.me/md-block.js" defer></script>--%>


<script>

    let postContent = '${post.getContent()}';

    const viewer = toastui.Editor.factory({
        el: document.querySelector('#viewer'),
        viewer: true,
        initialValue: postContent
    });

    const showModal = () => {

    }



</script>

