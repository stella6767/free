@import com.stella.free.web.page.layout.footer.FooterViewComponent
@import de.tschuehly.spring.viewcomponent.jte.ViewContext
@import jakarta.servlet.http.HttpSession
@import org.springframework.security.authentication.AnonymousAuthenticationToken
@import org.springframework.security.core.Authentication
@import org.springframework.security.core.context.SecurityContextHolder

@param String introduction



<link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css"/>

<div>

    <div class="hero min-h-screen " style="background-image: url(/img/cover.jpeg);">
        <div class="hero-overlay bg-opacity-60"></div>
        <div class="hero-content text-center text-neutral-content">
            <div class="max-w-md">
                <h1 class="mb-5 text-5xl font-bold">Hello everyone!
                    <br/> Try This simple Web </h1>
                <div id="viewer"></div>
                <p class="mb-5">Provide by stella6767</p>

            </div>
        </div>
    </div>

</div>

<script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>

<script>

    let introduction = '${introduction}';

    const viewer = toastui.Editor.factory({
        el: document.querySelector('#viewer'),
        viewer: true,
        initialValue: introduction
    });

</script>





