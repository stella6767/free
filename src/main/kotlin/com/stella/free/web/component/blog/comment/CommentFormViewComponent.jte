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


!{var content = @`


    @if(!isLogin)
        <div class="px-3 mb-2 mt-2">
            <input type="text"
                   required
                   name="nickName"
                   placeholder="input your nickname"
                   class="input input-bordered"/>

            <input type="text"
                   required
                   name="password"
                   placeholder="input your password"
                   class="input input-bordered"/>
        </div>

    @else

        <div class="px-3 mb-2 mt-2">
            <input type="text"
                   required
                   name="nickName"
                   placeholder="input your nickname"
                   class="input input-bordered"/>

        </div>

        <input type="hidden" name="userId" value="${((UserPrincipal)authentication.getPrincipal()).getUser().getId()}">

    @endif

    <input type="hidden" name="postId" value="${postId}">
    <input type="hidden" name="idAncestor" value="${idAncestor}">
    <input type="hidden" name="paddingLeft" value="${paddingLeft}">





    <div class="px-3 mb-2 mt-2">
            <textarea placeholder="comment"
                      name="content"
                      class="w-full bg-gray-100 rounded border border-gray-400 leading-normal resize-none h-20 py-2 px-3 font-medium placeholder-gray-700 focus:outline-none focus:bg-white">
<%--                      disabled="${!isLogin}"--%>
            </textarea>
    </div>


    <div class="flex justify-end px-4">
        <input type="submit" class="px-2.5 py-1.5 rounded-md text-white text-sm bg-indigo-500 cursor-pointer" x-on:click="open = ! open">
    </div>

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





