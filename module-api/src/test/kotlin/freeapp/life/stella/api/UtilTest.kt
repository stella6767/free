package freeapp.life.stella.api

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode
import org.jsoup.parser.Tag
import org.junit.jupiter.api.Test


class UtilTest {

    @Test
    fun closedTagTest(){

        val html = """
            <h1>TEST</h1>
            <p>sdf</p>
            <p>sdfsdf</p>
            <p>sdfsdf</p>
            <p><img src="https://free-stella.s3.ap-northeast-2.amazonaws.com/87af586b-d382-491d-870e-0ae1599cf1d0_25257E4753D84EE013.jpeg" alt="사진 대체 텍스트 입력" contenteditable="false"><br></p>
            <p><br></p>
            <p>짝이 맞는 br태그</p>           
        """.trimIndent() // Sample HTML with unclosed tags
        // Parse the HTML with Jsoup
        val document = Jsoup.parse(html)
        // Output the cleaned HTML



        // 모든 img 태그와 br 태그를 찾아서 <img>를 <img/>로, <br>을 <br/>로 변환
        for (element in document.select("img, br")) {
            if (element.tagName().equals("img") || element.tagName().equals("br")) {
                convertToSelfClosing(element)
            }
        }

        val cleanedHtml: String = document.body().html() .replace("&lt;", "<")
            .replace("&gt;", ">")

//        println("!!!!")
//        println(bodyHtml)


        println("Original HTML:")
        println(html)

        println("\nCleaned HTML:")
        println(cleanedHtml)
    }


    private fun convertToSelfClosing(element: Element) {
        val tag: StringBuilder = StringBuilder("<").append(element.tagName())
        element.attributes().forEach { attr ->
            tag.append(" ").append(attr.key).append("=\"").append(attr.value).append("\"")
        }
        tag.append(" />")
        //println(tag.toString())
        element.replaceWith(TextNode(tag.toString()))
    }

}