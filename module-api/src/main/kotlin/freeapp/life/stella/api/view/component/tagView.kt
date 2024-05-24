package freeapp.life.stella.api.view.component

import kotlinx.html.A
import kotlinx.html.DIV
import kotlinx.html.a
import kotlinx.html.classes


fun DIV.tagView(tagName: String) {


    a {
        href = "/blog/tag?tagName=${tagName}"
        classes = setOf(
            "h-8",
            "inline-flex",
            "items-center",
            "bg-black",
            "text-white",
            "cursor-pointer",
            "no-underline",
            "font-medium",
            "text-base",
            "mr-3.5",
            "px-4",
            "rounded-2xl"
        )
        +"$tagName"
    }


}
