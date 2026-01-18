package freeapp.life.stella.api.util

import com.opencsv.CSVReader
import freeapp.life.stella.storage.entity.type.SignType

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.io.RandomAccessFile
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import java.util.stream.Collectors
import kotlin.io.inputStream


class UtilTest {

    private val NAME_KEYS = listOf(
        "시설명", "시설명(업소)", "건물명", "상호명", "시설 구분", "구분", "설치 주체"
    )

    private val ADDRESS_KEYS = listOf(
        "도로명주소",
        "소재지도로명주소",
        "주소",
        "지번주소",
        "설치위치",
        "설치 위치",
        "서울특별시 용산구 설치 위치"
    )


    @Test
    fun readCsv() {

        val dirPath = "src/test/resources/csv-utf8"

        File(dirPath).listFiles { file ->
            file.extension.lowercase() == "csv"
        }?.forEach { csv ->
            println("===== ${csv.name} =====")

            csv.bufferedReader(StandardCharsets.UTF_8).useLines { lines ->
                lines.take(2).forEach { println(it) }
//                lines.forEach { line ->
//                    println(line)
//                }
            }
        }
    }



    @Test
    fun convertCsvDirectoryToUtf8() {

        val inputDir = File("src/test/resources/csv")
        val outputDir = File("src/test/resources/csv-utf8")

        require(inputDir.isDirectory) { "inputDir must be a directory" }
        outputDir.mkdirs()

        inputDir.listFiles { file ->
            file.extension.lowercase() == "csv"
        }?.forEach { csv ->
            val sourceCharset = detectCharset(csv)

            println("Converting ${csv.name} [$sourceCharset → UTF-8]")

            val text = csv.readText(sourceCharset)

            val outputFile = File(outputDir, csv.name)
            outputFile.writeText(text, StandardCharsets.UTF_8)
        }
    }


    @Test
    fun convert() {

        convertTsvToCsv(
            File("src/test/resources/csv/서울특별시 용산구_흡연구역_20240719.csv"),
            File("src/test/resources/csv-utf8/서울특별시 용산구_흡연구역_20240719.csv"),
            StandardCharsets.UTF_8
        )


    }

    fun convertTsvToCsv(
        input: File,
        output: File,
        sourceCharset: Charset = StandardCharsets.UTF_8
    ) {
        input.bufferedReader(sourceCharset).useLines { lines ->
            output.bufferedWriter(StandardCharsets.UTF_8).use { writer ->
                lines.forEach { line ->
                    val csvLine = line
                        .split('\t')
                        .joinToString(",") { value ->
                            if (value.contains(","))
                                "\"$value\""
                            else value
                        }
                    writer.write(csvLine)
                    writer.newLine()
                }
            }
        }
    }


    fun detectCharset(file: File): Charset {
        file.inputStream().use { input ->
            val bom = ByteArray(3)
            val read = input.read(bom)

            if (read >= 2) {
                // UTF-16 LE BOM: FF FE
                if (bom[0] == 0xFF.toByte() && bom[1] == 0xFE.toByte()) {
                    return StandardCharsets.UTF_16LE
                }
                // UTF-16 BE BOM: FE FF
                if (bom[0] == 0xFE.toByte() && bom[1] == 0xFF.toByte()) {
                    return StandardCharsets.UTF_16BE
                }
            }
            if (read == 3) {
                // UTF-8 BOM: EF BB BF
                if (bom[0] == 0xEF.toByte() &&
                    bom[1] == 0xBB.toByte() &&
                    bom[2] == 0xBF.toByte()
                ) {
                    return StandardCharsets.UTF_8
                }
            }
        }

        // BOM 없으면 → 현실적으로 CP949가 제일 안전
        return Charset.forName("x-windows-949")
    }


    @Test
    fun createOver100mbFile() {
        val target: Path = Path.of("src/test/resources/dummy-200mb.bin")
        Files.createDirectories(target.parent)

        RandomAccessFile(target.toFile(), "rw").use { raf ->
            raf.setLength(200L * 1024 * 1024) // 200MB
        }

        println("Created: ${target.toAbsolutePath()} size=${Files.size(target)} bytes")
    }


    @Test
    fun folderTest() {

        val string = "blog" + File.separator + "2023"

        println(string)

    }


    @Test
    fun getSocialTypes() {

        val socialTypes = SignType.Companion.getSocialTypes()

        println(socialTypes)
    }


    @Test
    fun downloadStreamingFileTest() {
        //val interceptor = PlaywrightInterceptor()

        val encoder: BCryptPasswordEncoder = BCryptPasswordEncoder()

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