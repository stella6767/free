@import org.springframework.security.authentication.AnonymousAuthenticationToken
@import org.springframework.security.core.Authentication
@import org.springframework.security.core.context.SecurityContextHolder

@param Long postId
@param Authentication authentication

<dialog id="post_modal" class="modal">
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

                    <div
                            class="btn">수정
                    </div>
                    <div hx-delete="/post/${postId}"
                         hx-swap="none"
                         hx-include="[name='password']"
                         class="btn ">삭제
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

