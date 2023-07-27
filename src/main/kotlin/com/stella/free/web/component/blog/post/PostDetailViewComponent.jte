@import gg.jte.Content

@param com.stella.free.core.blog.dto.PostDetailDto post
@param com.stella.free.web.component.blog.comment.CommentSectionViewComponent commentSectionViewComponent
@param Content content

<link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css"/>
<%--<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/github-markdown-css/5.2.0/github-markdown-light.css" integrity="sha512-n5zPz6LZB0QV1eraRj4OOxRbsV7a12eAGfFcrJ4bBFxxAwwYDp542z5M0w24tKPEhKk2QzjjIpR5hpOjJtGGoA==" crossorigin="anonymous" referrerpolicy="no-referrer" />--%>

<div class="w-3/6 mx-auto mb-5">

    <div
            class="sm:text-3xl md:text-3xl lg:text-3xl xl:text-4xl font-bold cursor-pointer">
        ${post.getTitle()}

    </div>


    <div class="font-light text-gray-600">
        <h1 class="font-bold text-gray-700 hover:underline">
            By ${post.getUsername()} · ${post.getCreatedAt()}
        </h1>
    </div>

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


</script>

