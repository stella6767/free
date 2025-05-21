package freeapp.life.stella.api.service

import com.microsoft.playwright.BrowserType.LaunchOptions
import com.microsoft.playwright.Playwright
import com.microsoft.playwright.Route
import com.microsoft.playwright.options.LoadState
import mu.KotlinLogging
import org.apache.commons.io.IOUtils
import java.io.File
import java.net.URI
import java.nio.charset.StandardCharsets
import java.util.concurrent.CountDownLatch
import java.util.regex.Pattern


class PlaywrightInterceptor(

) {

    private val log  = KotlinLogging.logger {  }


    fun getM3U8variantWithHighestBitrate(masterPlaylistUrl: String): String {

        println("Finding highest bitrate in Master/Playlist file URL >>> $masterPlaylistUrl ")

        val url = URI(masterPlaylistUrl).toURL()

        var highestBitRateM3U8url = url.openStream().use { `is` ->

            val playlistFileIO: String = IOUtils.toString(`is`, StandardCharsets.UTF_8)
            println("Printing content of master playlist file >>> $playlistFileIO")
            // Find all bit rate variants
            val bitrateMatcher =
                Pattern.compile("[^AVERAGE-]BANDWIDTH=([0-9]+)", Pattern.MULTILINE).matcher(playlistFileIO)
            // Find and get all *.m3u8 file paths
            val m3u8Matcher =
                Pattern.compile("^.*\\.m3u8.*", Pattern.MULTILINE).matcher(playlistFileIO)

            var highestBitRate = 0
            var highestBitRateIdx = 0
            var idxCounter = 0

            println("Finding highest bitrate...")

            while (bitrateMatcher.find()) {

                val tempBitRate =
                    bitrateMatcher.group(1).toInt()

                println("tempBitRate - $tempBitRate")

                if (highestBitRate < tempBitRate) {
                    highestBitRate = tempBitRate
                    highestBitRateIdx = idxCounter
                    println("New highestBitRate - $highestBitRate at index - $highestBitRateIdx")
                }

                idxCounter++
            }

            val m3u8MatcherList: MutableList<String> = ArrayList()
            while (m3u8Matcher.find()) {
                println("m3u8Matcher - " + m3u8Matcher.group())
                m3u8MatcherList.add(m3u8Matcher.group())
            }
            println("Highest bit rate m3u8 match - ${m3u8MatcherList[highestBitRateIdx]}")

            m3u8MatcherList[highestBitRateIdx]
        }


        if (!highestBitRateM3U8url.startsWith("http:") && !highestBitRateM3U8url.startsWith("https:")) {

            println(
                ("WARN! WARN! The following file location is not an absolute path URL!\n" +
                        " Current relative path = " + highestBitRateM3U8url + " Absolute path will be based on current on the M3U8 file path: " + masterPlaylistUrl)
            )
            // Using M3U8 url to construct absolute paths for ts file urls
            highestBitRateM3U8url = getAbsolutePath(masterPlaylistUrl, highestBitRateM3U8url)
            println("New absolute file path url=>$highestBitRateM3U8url")
        }

        return highestBitRateM3U8url
    }

    private fun getAbsolutePath(baseAbsolutePath: String, relativePath: String?): String {
        val idx = baseAbsolutePath.lastIndexOf(File.separator)
        val base = baseAbsolutePath.substring(0, idx + 1)
        val absolutePath = base + relativePath
        return absolutePath
    }

    fun findMasterM3U(listOfHarM3u8files: List<String>): String {

        println("Inspecting list of HAR m3u8 files to identify mater playlist")
        for ((counter, m3uFileUrl: String) in listOfHarM3u8files.withIndex()) {
            var url = URI(m3uFileUrl).toURL()
            url.openStream().use { `is` ->
                val playlistFileIO: String = IOUtils.toString(`is`, StandardCharsets.UTF_8)
                println("\n$counter. Printing content of m3u8 file\n$playlistFileIO")
                if (playlistFileIO.contains("EXT-X-STREAM-INF")) {
                    println("FOUND Master/Playlist m3u8 url: $m3uFileUrl")
                    return m3uFileUrl
                }
            }
        }

        println("WARNING! No m3u8 master playlist found!")
        return ""
    }


    fun retrieveM3U8requestFiles(url: String): List<String> {


        val latch = CountDownLatch(1)

        // m3u8 URL들을 저장할 리스트
        val m3u8Urls = mutableListOf<String>()

        Playwright.create().use { playwright ->
            // 브라우저 실행
//            val browser = playwright.chromium().launch(
//                BrowserType.LaunchOptions()
//                    .setHeadless(true)
//                    .setDevtools(true) // 개발자 도구 자동 열기
//            )

            //val browser = playwright.chromium().launch()
                //.launch(BrowserType.LaunchOptions())


            val browser = playwright.chromium().launch(LaunchOptions().setHeadless(false))


//            val contextOptions = Browser.NewContextOptions()
//            contextOptions.setIgnoreHTTPSErrors(true)
//
//            val context = browser.newContext(contextOptions)
//            val context = browser.launchPersistentContext(
//                Path.of("nik_nesterenko_2020@mail.ru"),
//                LaunchPersistentContextOptions()
//                    .setHeadless(true)
//                    .setArgs(
//                        mutableListOf<String>(
//                            "--disable-blink-features=AutomationControlled",
//                            "--blink-settings=imagesEnabled=false"
//                        )
//                    )
//                    .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36")
//                    .setJavaScriptEnabled(true)
//                    .setBypassCSP(true)
//                    .setTimeout(100000.0)
//            )

//            val harFile = Paths.get("/Users/stella6767/IdeaProjects/todo-htmx/module-api/src/main/resources/test.har")
//            val context: BrowserContext = browser.newContext(Browser.NewContextOptions().setRecordHarPath(harFile))

            // 새로운 페이지 생성
            val page = browser.newPage()


            // 네트워크 요청 이벤트 리스너 등록
//            page.onRequest { request ->
//                val url = request.url()
//
//                //println(url)
//                if (url.contains(".m3u8") || url.contains(".mp4")) {
//                    m3u8Urls.add(url)
//                    log.info("Found target URL, releasing latch")
//                    if (url.contains("m3u8")){
//                        //latch.countDown()  // URL을 찾았을 때 래치 해제
//                    }
//                }
//
//            }

            page.route("**") { route: Route ->
                // Log all requests
                val url = route.request().url()

                if (url.contains(".m3u8") || url.contains(".mp4")) {
                    m3u8Urls.add(url)
                    log.info("Found target URL, releasing latch")
                    println("Request: " + url)
                    if (url.contains("m3u8")) {
                        //latch.countDown()  // URL을 찾았을 때 래치 해제
                    }
                }

                route.resume() // Resume request
            }


//            page.route("**/*") {
//
//                val url = it.request().url()
//
//                if (url.contains(".m3u8") || url.contains(".mp4")) {
//                    m3u8Urls.add(url)
//                    log.info("Found target URL, releasing latch")
//                    if (url.contains("m3u8")){
//                        //latch.countDown()  // URL을 찾았을 때 래치 해제
//                    }
//                }
//
//            }


            page.navigate(url) //웹 페이지로 이동

            // 페이지가 로드될 때까지 대기
            page.waitForLoadState(LoadState.LOAD)

            Thread.sleep(3000)

            //val found = latch.await(100, TimeUnit.SECONDS) //찾을 때까지 대기

//            if (found) {
//                log.info("Target URL encountered, proceeding with further steps")
//            } else {
//                log.info("Timeout reached, target URL not encountered")
//            }

            // 브라우저 종료
            browser.close()
        }

        // 수집된 m3u8 URL들 출력
        //println("Collected m3u8 URLs: $m3u8Urls")

        return m3u8Urls
    }


}
