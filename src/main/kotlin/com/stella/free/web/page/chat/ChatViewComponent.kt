package com.stella.free.web.page.chat

import com.stella.free.web.component.chat.ChatBoxViewComponent
import de.tschuehly.spring.viewcomponent.core.ViewComponent
import de.tschuehly.spring.viewcomponent.jte.ViewContext


@ViewComponent
class ChatViewComponent(
    private val chatBoxViewComponent: ChatBoxViewComponent,
) {

    fun render(): ViewContext {

        return ViewContext(

        )

    }

}