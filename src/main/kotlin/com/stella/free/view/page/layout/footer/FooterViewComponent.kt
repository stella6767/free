package com.stella.free.view.page.layout.footer


import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext

@ViewComponent
class FooterViewComponent(

)  {

    fun render(): ViewContext {

        return ViewContext(
            "githubUrl" toProperty "https://github.com/stella6767",
            "creator" toProperty "Stella6767",
            "creatorEmail" toProperty "alsrb9434@gmail.com",
        )

    }
}