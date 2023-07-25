package com.stella.free.web.component.auth

import com.stella.free.core.account.entity.type.SignType

import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext

@ViewComponent
class LoginModalViewComponent(
) {


    fun render(): ViewContext {

        val signTypes =
            SignType.values()

        return ViewContext(
            "signTypes" toProperty signTypes,

        )

    }

}