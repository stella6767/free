@import com.stella.free.global.config.security.UserPrincipal
@import org.springframework.security.authentication.AnonymousAuthenticationToken

@param org.springframework.security.core.Authentication authentication
@param Boolean isLogin
@param com.stella.free.core.account.entity.type.SignType signType 
@param Long postId
@param Long idAncestor
@param Integer paddingLeft


<%--@if(idAncestor != 0)--%>
<%--<form class="bg-white rounded-lg border p-2 mx-auto "--%>
<%--      id="comment-form"--%>
<%--      hx-post="/comment"--%>
<%--      hx-target="#comment-card-${idAncestor}"--%>
<%--      hx-swap="beforeend"--%>
<%-->--%>
<%--@else--%>
<%--@endif--%>

com.munute.munuteadmin.entity.account

com.munute.back.domain.payment.entity

import com.munute.back.global.config.jpa.BaseEntity
import com.munute.munuteadmin.entity.BaseEntity

!{var content = @`
    <div class="px-3 mb-2 mt-2">
            <textarea placeholder="comment"
                      disabled="${!isLogin}"
                      name="content"
                      class="w-full bg-gray-100 rounded border border-gray-400 leading-normal resize-none h-20 py-2 px-3 font-medium placeholder-gray-700 focus:outline-none focus:bg-white">
            </textarea>
    </div>

    @if(!isLogin)
        <div class="gsc-comment-box-bottom">
            <div class="gsc-comment-box-buttons flex justify-end">
                <a target="_top" href="${signType.getAuthorizationUrl()}"
                   class="btn btn-primary inline-flex items-center ml-1 rounded-md border hover:no-underline">
                    <svg aria-hidden="true" focusable="false" role="img" viewBox="0 0 16 16" width="16" height="16"
                         fill="currentColor" class="mr-2"
                         style="display: inline-block; user-select: none; vertical-align: text-bottom; overflow: visible;">
                        <path d="M8 0c4.42 0 8 3.58 8 8a8.013 8.013 0 0 1-5.45 7.59c-.4.08-.55-.17-.55-.38 0-.27.01-1.13.01-2.2 0-.75-.25-1.23-.54-1.48 1.78-.2 3.65-.88 3.65-3.95 0-.88-.31-1.59-.82-2.15.08-.2.36-1.02-.08-2.12 0 0-.67-.22-2.2.82-.64-.18-1.32-.27-2-.27-.68 0-1.36.09-2 .27-1.53-1.03-2.2-.82-2.2-.82-.44 1.1-.16 1.92-.08 2.12-.51.56-.82 1.28-.82 2.15 0 3.06 1.86 3.75 3.64 3.95-.23.2-.44.55-.51 1.07-.46.21-1.61.55-2.33-.66-.15-.24-.6-.83-1.23-.82-.67.01-.27.38.01.53.34.19.73.9.82 1.13.16.45.68 1.31 2.69.94 0 .67.01 1.3.01 1.49 0 .21-.15.45-.55.38A7.995 7.995 0 0 1 0 8c0-4.42 3.58-8 8-8Z"></path>
                    </svg>
                    Sign in with ${signType.name()}</a></div>
        </div>
    @else
        <input type="hidden" name="userId" value="${((UserPrincipal)authentication.getPrincipal()).getUser().getId()}">
        <input type="hidden" name="postId" value="${postId}">
        <input type="hidden" name="idAncestor" value="${idAncestor}">
        <input type="hidden" name="paddingLeft" value="${paddingLeft}">

        <div class="flex justify-end px-4">
            <input type="submit" class="px-2.5 py-1.5 rounded-md text-white text-sm bg-indigo-500 cursor-pointer" x-on:click="open = ! open">
        </div>
    @endif

`;}




@if(idAncestor != 0)
    <form class="bg-white rounded-lg border p-2 mx-auto "
          id="comment-form-${idAncestor}"
          hx-post="/comment"
          hx-swap="beforeend"
          hx-target="#comment-card-${idAncestor}"
    >
        ${content}
    </form>
@else

    <form class="bg-white rounded-lg border p-2 mx-auto "
          id="comment-form-${idAncestor}"
          hx-post="/comment"
          hx-target="#comment-card-container"
          hx-swap="beforeend"
    >
        ${content}
    </form>

@endif





