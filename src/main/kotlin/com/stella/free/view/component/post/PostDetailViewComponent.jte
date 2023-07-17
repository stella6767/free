@param com.stella.free.dto.PostDetailDto post

<link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css"/>

<div class="w-3/6 mx-auto mb-2">

    <div
            class="sm:text-3xl md:text-3xl lg:text-3xl xl:text-4xl font-bold cursor-pointer">
        ${post.getTitle()}
    </div>


    <div class="font-light text-gray-600">
        <h1 class="font-bold text-gray-700 hover:underline">By ${post.getUsername()}</h1>
    </div>

</div>


<div id="viewer">${post.getContent()}</div>


<%--<span onclick="showContent('${post.getContent()}')">Click me</span>--%>


<style>


    #viewer {
        width: 50%;
        margin: 0 auto;
    }

</style>

<script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>


<script>

    const viewer = toastui.Editor.factory({
        el: document.querySelector('#viewer'),
        viewer: true,
        //initialValue: "I want to go home"
    });


</script>

