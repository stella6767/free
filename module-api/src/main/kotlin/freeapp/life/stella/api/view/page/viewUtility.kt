package freeapp.life.stella.api.view.page

import kotlinx.html.*

@HtmlTagMarker
inline fun FlowContent.path(classes: String? = null, crossinline block: PATH.() -> Unit = {}): Unit = PATH(
    attributesMapOf("class", classes), consumer
).visit(block)

@Suppress("unused")
open class PATH(initialAttributes: Map<String, String>, override val consumer: TagConsumer<*>) :
    HTMLTag("path", consumer, initialAttributes, null, false, false), HtmlBlockTag {}




@HtmlTagMarker
inline fun DIV.markDown(classes: String? = null, crossinline block: MDBLOCK.() -> Unit = {}): Unit = MDBLOCK (
    attributesMapOf("class", classes), consumer
).visit(block)

@Suppress("unused")
open class MDBLOCK(initialAttributes: Map<String, String>, override val consumer: TagConsumer<*>) :
    HTMLTag("md-block", consumer, initialAttributes, null, false, false), HtmlBlockTag {}