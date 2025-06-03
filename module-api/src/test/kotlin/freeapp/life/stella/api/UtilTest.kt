package freeapp.life.stella.api

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

import org.testng.annotations.Test
import java.util.*


class UtilTest {


    @Test
    fun downloadStreamingFileTest() {
        //val interceptor = PlaywrightInterceptor()

        val encoder: BCryptPasswordEncoder =BCryptPasswordEncoder()

        val encode = encoder.encode("stella6767")
        println(encode)


    }


    @Test
    fun m3u8DownloadTest() {

//        val url = ""
//
//        val interceptor = PlaywrightInterceptor()
//        //val u8requestFiles = interceptor.retrieveM3U8requestFiles(url)
//
//        val m3U8variantWithHighestBitrate = interceptor.getM3U8variantWithHighestBitrate(url)
//
//        println(m3U8variantWithHighestBitrate)
//        //println(u8requestFiles)

    }

    @Test
    fun playwrightTest() {

        val url = ""

    }


    @Test
    fun mdBlockTest() {

        val md = """
            # Heading
        
            Text **bold text** *italic text* ~strike~
        
            ```js
            test.foo();
            ```
        
            ```html
            &lt;foo>
            ```
        
            * List item 1
            * List item 2
   
   
            	
        """.trimIndent()


    }


    @Test
    fun unclosedTagTest() {


        val html = """
            <h1>TEST</h1>
            <p>sdf</p>
            <p>sdfsdf</p>
            <p>sdfsdf</p>
            <p><img src="https://free-stella.s3.ap-northeast-2.amazonaws.com/87af586b-d382-491d-870e-0ae1599cf1d0_25257E4753D84EE013.jpeg" alt="사진 대체 텍스트 입력" contenteditable="false"><br></p>
            <p><br></p>
            <p>짝이 맞는 br태그</p>           
        """.trimIndent() // Sample HTML with unclosed tags



        println(autoComplete(html))
    }


    fun autoComplete(s: String): String {
        // Split the HTML code into lines
        val linesOfCode: MutableList<String> = ArrayList()
        val line = java.lang.StringBuilder()
        for (ch in s.toCharArray()) {
            if (ch == '\n') {
                linesOfCode.add(line.toString())
                line.setLength(0)
            } else {
                line.append(ch)
            }
        }
        if (line.isNotEmpty()) {
            linesOfCode.add(line.toString())
        }


        // Tags which are self-closed and don't need closing
        val selfClosedTags: List<String> = listOf(
            "area",
            "base",
            "br",
            "col",
            "embed",
            "hr",
            "img",
            "input",
            "link",
            "meta",
            "param",
            "source",
            "track",
            "wbr"
        )

        val stack: Stack<String> = Stack()


        // Loop to iterate over the lines of code
        for (lineCode in linesOfCode) {
            var j = 0
            while (j < lineCode.length) {
                // Check for end tags
                if (j + 1 < lineCode.length && lineCode[j] == '<' && lineCode[j + 1] == '/') {
                    val tag = java.lang.StringBuilder()
                    j += 2
                    while (j < lineCode.length && Character.isLowerCase(lineCode[j])) {
                        tag.append(lineCode[j])
                        j++
                    }
                    while (j < lineCode.length && lineCode[j] != '>') {
                        j++
                    }
                    if (!stack.isEmpty() && !stack.peek().equals(tag.toString())) {
                        return ("</" + stack.peek()).toString() + ">"
                    }
                    stack.pop()
                } else if (j + 1 < lineCode.length && lineCode[j] == '<' && lineCode[j + 1] == '!') {
                    j += 2
                } else if (lineCode[j] == '<') {
                    val tag = java.lang.StringBuilder()
                    j++
                    while (j < lineCode.length && Character.isLowerCase(lineCode[j])) {
                        tag.append(lineCode[j])
                        j++
                    }
                    while (j < lineCode.length && lineCode[j] != '>') {
                        j++
                    }
                    if (!selfClosedTags.contains(tag.toString())) {
                        stack.push(tag.toString())
                    }
                }
                j++
            }
        }


        // Check if any tag is unbalanced then return that tag
        if (!stack.isEmpty()) {
            return ("</" + stack.peek()).toString() + ">"
        }
        return "-1"
    }


    @Test
    fun closedTagTest() {

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
        for (element in document.select("img, array, br, dependencies, area, base, col, embed, hr, input, link, meta, param, source, track, wbr")) {
            if (element.tagName().equals("img") || element.tagName().equals("br")) {
                convertToSelfClosing(element)
            }
        }

        val cleanedHtml: String = document.body().html()
            .replace("&lt;", "<")
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
