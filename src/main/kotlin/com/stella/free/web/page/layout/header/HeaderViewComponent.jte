@import org.springframework.security.authentication.AnonymousAuthenticationToken
@import org.springframework.security.core.context.SecurityContextHolder
@import com.stella.free.web.component.auth.LoginModalViewComponent
@import com.stella.free.web.component.common.ProgressViewComponent

@param LoginModalViewComponent loginModalViewComponent
@param ProgressViewComponent progressViewComponent

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Whatever</title>

    <script src="https://unpkg.com/htmx.org@1.9.2"></script>
<%--    <script src="https://unpkg.com/htmx.org/dist/ext/ws.js"></script>--%>
    <link href="https://cdn.jsdelivr.net/npm/daisyui@3.0.3/dist/full.css" rel="stylesheet" type="text/css"/>
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="/js/client.js" defer></script>

    <script src="https://unpkg.com/htmx.org/dist/ext/loading-states.js"></script>

    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" crossorigin="anonymous">
    <script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>
    <script defer src="https://cdn.jsdelivr.net/npm/alpinejs@3.12.1/dist/cdn.min.js"></script>

    <style>
        .htmx-settling img {
            opacity: 0;
        }

        img {
            transition: opacity 300ms ease-in;
        }
    </style>

</head>
<div class="navbar bg-base-100 sticky top-0 z-30">
    <div class="drawer navbar-start">
        <input id="my-drawer" type="checkbox" class="drawer-toggle"/>
        <div class="drawer-content ml-5">
            <!-- Page content here -->
            <label for="my-drawer" class="drawer-button">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                     class="inline-block w-5 h-5 stroke-current">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                          d="M4 6h16M4 12h16M4 18h16"></path>
                </svg>
            </label>
        </div>
        <div class="drawer-side z-10">
            <label for="my-drawer" class="drawer-overlay"></label>
            <ul class="menu p-4 w-80 h-full bg-base-200 text-base-content">
                <!-- Sidebar content here -->
                <li><a href="/resume">about me</a></li>
                <li><a href="/todos">todos</a></li>
                <li><a href="/blog">blog</a></li>
                <li><a href="/publicApis">public Api List</a></li>
                <li><a href="/chat">Chat Lab</a></li>
                <li><a href="/velog/crawler">velog crawler</a></li>
            </ul>
        </div>
    </div>

    <div class="navbar-center">
        <a class="btn btn-ghost normal-case text-xl mt-3" href="/">Whatever</a>
    </div>



    <div class="navbar-end">

<%--        hx-get="/posts?page=0"--%>
<%--        hx-target="#posts-container"--%>
<%--        hx-swap="innerHTML"--%>

        <div class="max-w-md">
            <form
                 method="get"
                 action="/blog"
                 class="relative mx-auto w-max">
                <input type="search"
                       name="keyword"
                       class="peer cursor-pointer relative z-10 h-12 w-12 rounded-full border bg-transparent pl-12 outline-none focus:w-full focus:cursor-text focus:border-lime-300 focus:pl-16 focus:pr-4" />
                <svg xmlns="http://www.w3.org/2000/svg" class="absolute inset-y-0 my-auto h-8 w-12 border-r border-transparent stroke-gray-500 px-3.5 peer-focus:border-lime-300 peer-focus:stroke-lime-500" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
                </svg>
            </form>
        </div>

        <ul class="menu menu-horizontal px-1 mr-5">
            <li>
                <details>
                    <summary>
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                             class="inline-block w-5 h-5 stroke-current">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                  d="M5 12h.01M12 12h.01M19 12h.01M6 12a1 1 0 11-2 0 1 1 0 012 0zm7 0a1 1 0 11-2 0 1 1 0 012 0zm7 0a1 1 0 11-2 0 1 1 0 012 0z"></path>
                        </svg>
                    </summary>
                    <ul class="p-2 bg-base-100">

                        @if(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)
                            <li><a onclick="login_modal.showModal()">Login</a></li>
                        @else
                            <li><a href="/logout">LogOut</a></li>
                            <li><a href="/post/editor">새글쓰기</a></li>
                        @endif
                    </ul>
                </details>
            </li>
        </ul>
    </div>
</div>

${loginModalViewComponent.render()}


${progressViewComponent.render()}

<br/>

</html>

