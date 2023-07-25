@param com.stella.free.core.blog.dto.PostDetailDto post
@param com.stella.free.web.component.blog.CommentSectionViewComponent commentSectionViewComponent

<%--<link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css"/>--%>

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


<div id="viewer">
    $unsafe{post.getContent()}
</div>



${commentSectionViewComponent.render()}

<%--<span onclick="showContent('${post.getContent()}')">Click me</span>--%>


<style>


    #viewer {
        width: 50%;
        margin: 0 auto;
    }

</style>

<script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>


<script>

    //console.log(${post.getContent()});

    // const viewer = toastui.Editor.factory({
    //     el: document.querySelector('#viewer'),
    //     viewer: true,
    //     //initialValue: "I want to go home"
    // });


</script>

