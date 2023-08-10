@import org.springframework.security.authentication.AnonymousAuthenticationToken
@import org.springframework.security.core.Authentication
@import org.springframework.security.core.context.SecurityContextHolder


@param Authentication authentication
@param com.stella.free.core.blog.dto.PostDetailDto post

<dialog id="post_delete_modal" class="modal">
    <form method="dialog" class="modal-box">


<%--        @if(authentication instanceof AnonymousAuthenticationToken)--%>
<%--            <input type="text" name="password" placeholder="비밀번호를 입력해주세요"--%>
<%--                   class="input input-bordered w-full mb-2"/>--%>
<%--        @else--%>
<%--            <input type="hidden" name="password" value=""/>--%>
<%--        @endif--%>

        <input type="text" name="password" placeholder="비밀번호를 입력해주세요"
               class="input input-bordered w-full mb-2"/>

        <div class="bg-white shadow w-full rounded-lg divide-y divide-gray-200">

            <div class="p-5">
                <div class="mt-3 space-x-2 flex justify-around">

                    <div hx-delete="/post/${post.getId()}"
                         hx-swap="none"
                         hx-include="[name='password']"
                         class="btn " id="post-delete-btn">삭제
                    </div>
                </div>
            </div>

        </div>


        <div class="modal-action">
            <!-- if there is a button in form, it will close the modal -->
            <button class="btn">Close</button>
        </div>
    </form>
</dialog>


<script>

    document.querySelector('#post-delete-btn').addEventListener('htmx:afterRequest', function (evt) {
        //var xhr = evt.detail.xhr;
        //var statusCode = xhr.status;
        let successful = evt.detail.successful;
        if (successful) {
            location.reload();
        }
    });

</script>
