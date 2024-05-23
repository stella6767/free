package freeapp.life.stella.api.view.component

import freeapp.life.stella.storage.entity.type.SignType
import kotlinx.html.*


fun DIV.loginModalView() {

    val signTypes = SignType.values()

    dialog {
        id = "login_modal"
        classes = setOf("modal")
        form {
            attributes["method"] = "dialog"
            classes = setOf("modal-box")
            h1 {
                classes = setOf("font-bold", "text-center", "text-2xl", "mb-5")
                +"Social Login"
            }
            div {
                classes = setOf("bg-white", "shadow", "w-full", "rounded-lg", "divide-y", "divide-gray-200")
                div {
                    classes = setOf("p-5")
                    div {
                        classes = setOf("mt-3", "grid", "space-y-4")

                        for (signType in signTypes) {

                            button {
                                classes = setOf(
                                    "group",
                                    "h-12",
                                    "px-6",
                                    "border-2",
                                    "border-gray-300",
                                    "rounded-full",
                                    "transition",
                                    "duration-300",
                                    "hover : border -blue - 400",
                                    "focus : bg -blue - 50",
                                    "active : bg -blue - 100"
                                )
                                attributes["onclick"] = "location.href='${signType.authorizationUrl}'"
                                div {
                                    classes =
                                        setOf("relative", "flex", "items-center", "space-x-4", "justify-center")
                                    img {
                                        src = "${signType.imgUrl}"
                                        classes = setOf("absolute", "left-0", "w-5")
                                        alt = "logo"
                                    }
                                    span {
                                        classes = setOf(
                                            "block",
                                            "w-max",
                                            "font-semibold",
                                            "tracking-wide",
                                            "text-gray-700",
                                            "text-sm",
                                            "transition",
                                            "duration-300",
                                            "group-hover:text-blue-600",
                                            "sm:text-base"
                                        )
                                        +"Continue with ${signType.clientName}"
                                    }
                                }
                            }
                            +"@endfor"
                        }
                    }

                }


            }
            div {
                classes = setOf("modal-action")
                button {
                    classes = setOf("btn")
                    +"Close"
                }
            }
        }
    }

}

