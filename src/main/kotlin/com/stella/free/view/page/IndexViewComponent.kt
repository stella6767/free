package com.stella.free.view.page

import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext
import org.springframework.security.core.context.SecurityContextHolder

@ViewComponent
class IndexViewComponent(

) {

    fun render(): ViewContext {

        val authentication =
            SecurityContextHolder.getContext().authentication

        println("??????")

        println(authentication.authorities)
        println(authentication.principal)


        return ViewContext(
            "hello" toProperty "is it JTE Worth?",
            "authentication" toProperty authentication,
        )

    }

}