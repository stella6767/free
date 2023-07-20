@import com.stella.free.web.page.layout.footer.FooterViewComponent
@import de.tschuehly.spring.viewcomponent.jte.ViewContext
@import jakarta.servlet.http.HttpSession
@import org.springframework.security.authentication.AnonymousAuthenticationToken
@import org.springframework.security.core.Authentication
@import org.springframework.security.core.context.SecurityContextHolder

@param String hello
@param Authentication authentication


<div>

    <div class="hero min-h-screen " style="background-image: url(/img/cover.jpeg);">
        <div class="hero-overlay bg-opacity-60"></div>
        <div class="hero-content text-center text-neutral-content">
            <div class="max-w-md">
                <h1 class="mb-5 text-5xl font-bold">Hello there <br/> Try This simple Todo App</h1>
                <p class="mb-5">Provide by stella6767</p>
                @if(authentication instanceof AnonymousAuthenticationToken)
                    <button class="btn btn-primary" onclick="location.href='/login/modal'">login</button>
                @else
                    <button class="btn btn-primary" onclick="location.href='/todos'">${hello}</button>
                @endif

            </div>
        </div>
    </div>

</div>






