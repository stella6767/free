@import org.springframework.security.authentication.AnonymousAuthenticationToken
@import org.springframework.security.core.context.SecurityContextHolder
@import com.stella.free.view.component.auth.LoginModalViewComponent

@param LoginModalViewComponent loginModalViewComponent

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Todo App</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">


    <script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>
    <link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css"/>

    <script src="https://unpkg.com/htmx.org@1.9.2"></script>
    <link href="https://cdn.jsdelivr.net/npm/daisyui@3.0.3/dist/full.css" rel="stylesheet" type="text/css"/>
    <script src="https://cdn.tailwindcss.com"></script>
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
                <li><a href="/">Home</a></li>
                <li><a href="/resume">about me</a></li>
                <li><a href="/todos">todos</a></li>
                <li><a href="/blog/home">blog</a></li>

            </ul>
        </div>
    </div>

    <div class="navbar-center">
        <a class="btn btn-ghost normal-case text-xl mt-3">Free App</a>
    </div>
    <div class="navbar-end">

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
                            <li><a href="/blog/post/editor">새글쓰기</a></li>
                        @endif


                    </ul>
                </details>
            </li>
        </ul>
    </div>
</div>

${loginModalViewComponent.render()}

<br/>



