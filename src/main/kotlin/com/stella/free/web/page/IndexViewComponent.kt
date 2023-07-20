package com.stella.free.web.page

import com.stella.free.global.util.logger
import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder

@ViewComponent
class IndexViewComponent(

) {

    private val log = logger()

    fun render(): ViewContext {

        return ViewContext(
            "hello" toProperty "is it JTE Worth?",
        )

    }

}