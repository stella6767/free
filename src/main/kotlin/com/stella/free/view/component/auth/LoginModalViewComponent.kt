package com.stella.free.view.component.auth

import com.stella.free.entity.type.SignType

import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.core.toProperty
import de.tschuehly.spring.viewcomponent.jte.ViewContext

@ViewComponent
class LoginModalViewComponent(
) {


    fun render(): ViewContext {

        val signTypes =
            SignType.values().map { it.name }

        return ViewContext(
            "signTypes" toProperty signTypes,

        )

    }

}