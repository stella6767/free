@import com.stella.free.web.page.layout.footer.FooterViewComponent
@import de.tschuehly.spring.viewcomponent.jte.ViewContext
@import jakarta.servlet.http.HttpSession
@import org.springframework.security.authentication.AnonymousAuthenticationToken
@import org.springframework.security.core.Authentication
@import org.springframework.security.core.context.SecurityContextHolder

@param String introduction



<link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css"/>

<div>

    <div class="hero min-h-screen " style="background-image: url(/img/background.jpg);">
        <div class="hero-overlay bg-opacity-60"></div>
        <div class="hero-content text-center text-neutral-content">
            <div class="max-w-md">
                <h1 class="mb-5 text-3xl font-bold">Hello everyone!
                    <br/>My Simple Web Introduction</h1>

                <div class="toastui-editor-contents" style="overflow-wrap: break-word;">
                    <div data-nodeid="1">
                        $unsafe{introduction}
                    </div>
                </div>

                <p class="mb-5">Provide by stella6767</p>

            </div>
        </div>
    </div>

</div>

<script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>

<script>

    <%--let introduction = '${introduction}';--%>

    <%--const viewer = toastui.Editor.factory({--%>
    <%--    el: document.querySelector('#viewer'),--%>
    <%--    viewer: true,--%>
    <%--    initialValue: introduction--%>
    <%--});--%>

</script>





