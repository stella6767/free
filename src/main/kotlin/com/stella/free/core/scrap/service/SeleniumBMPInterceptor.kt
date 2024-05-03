package com.stella.free.core.scrap.service



import com.stella.free.global.util.logger
import net.lightbody.bmp.BrowserMobProxyServer
import net.lightbody.bmp.client.ClientUtil
import net.lightbody.bmp.proxy.CaptureType
import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.devtools.Command

import org.openqa.selenium.devtools.v124.network.Network
import org.openqa.selenium.devtools.v85.log.Log
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.DesiredCapabilities
import java.io.File
import java.net.Inet4Address
import java.time.Duration
import java.util.*
import java.util.function.Consumer


class SeleniumBMPInterceptor {


    private val log = logger()


    fun getDriver(url:String){
        val driver: ChromeDriver = ChromeDriver()

        driver.get(url)

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))

        val elements = driver.findElements(By.tagName("div"))
        for (element in elements) {
            System.out.println("----------------------------");
            System.out.println(element.text);
        }

        val devTools = driver.devTools
        devTools.createSession()
//        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
//
//
//
//        devTools.addListener(Network.responseReceived(), Consumer { l ->
//            print("Response URL: ")
//            System.out.println(l.getResponse().getUrl())
//        })
//        devTools.addListener(Network.requestWillBeSent(), Consumer { l ->
//            print("Request URL: ")
//            System.out.println(l.getRequest().getUrl())
//        })




        driver.quit()

    }


    fun retrieveM3U8requestFiles(websiteUrl: String): List<String> {

        log.info("Attempting to find m3u8 files in website >>> $websiteUrl")

        // Using Chrome as default browser.
        // For Support using Firefox or other browsers please download the corresponding driver
//        System.setProperty(
//            "webdriver.chrome.driver",
//            "/Users/stella6767/IdeaProjects/free/src/main/resources/static/drivers/chromedriver"
//        )


        /**
         * 1. proxy 서버 초기화 및 시작
         */

        val proxy = BrowserMobProxyServer()
        // Does your website request require custom headers?
        // (Cookies, User-agent, jwt, etc) Add them here
        proxy.addRequestFilter { request, contents, messageInfo ->
            request.headers().add("Connection", "keep-alive")
            request.headers().add("Upgrade-Insecure-Requests", "1")
            request.headers().add(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.6367.91 Safari/537.36"
            )
            request.headers().add(
                "Accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9"
            )
            request.headers().add("Accept-Language", "en-US,en;q=0.9,es;q=0.8")
            // request.headers().add("Cookie", "_ga=GA1.2.1472761358.1613093591; _gid=GA1.2.1777212447.1615090869; _gat=1; XSRF-TOKEN=eyJpdiI6IkZxZYWI2N2VkZmM1YTJjNjIwNjRjZmM2NDBiIn0%3D");
            log.info(request.headers().entries().toString())
            null
        }
        // proxy.blacklistRequests(".*google.*", 204);
        proxy.setTrustAllServers(true)
        proxy.start()


        /**
         *  2. Create the Selenium proxy to store all browser network communication.
         */

        val seleniumProxy = ClientUtil.createSeleniumProxy(proxy)
        val hostIp =
            Inet4Address.getLocalHost().hostAddress
        val proxyHost = hostIp + ":" + proxy.port
        println(proxyHost)
        seleniumProxy.setHttpProxy(proxyHost)
        seleniumProxy.setSslProxy(proxyHost)

        val seleniumCapabilities = DesiredCapabilities()
        seleniumCapabilities.setCapability(CapabilityType.PROXY, seleniumProxy)
        val options = ChromeOptions()
        options.addArguments("--ignore-certificate-errors")
        options.addArguments("--remote-allow-origins=*")
        //options.addArguments("--headless")
        //options.setCapability("ignoreProtectedModeSettings", true)
        options.merge(seleniumCapabilities)


        /**
         *  3. Instantiate Chrome browser with Capability and maximize the browser.
         */

        //WebDriverManager.chromedriver().setup()
        val driver: ChromeDriver = ChromeDriver(options)
        // Capture all event types
        proxy.enableHarCaptureTypes(
            CaptureType.REQUEST_HEADERS,
            CaptureType.REQUEST_COOKIES,
            CaptureType.REQUEST_CONTENT,
            CaptureType.REQUEST_BINARY_CONTENT,
            CaptureType.RESPONSE_HEADERS,
            CaptureType.RESPONSE_COOKIES,
            CaptureType.RESPONSE_CONTENT,
            CaptureType.RESPONSE_BINARY_CONTENT
        )

        // Create HTTP Archive (HAR) file for http tracing. (Script will attempt to capture all m3u8 requests produced from website loading)
        proxy.newHar("harCapture")
        driver[websiteUrl]
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))

        //driver.devTools

        // Start capture
        val storeHAR =
            "src/main/resources/static/httpArchiveOutput/trace-website-http-requests.har"
        val harFile = File(storeHAR)
        proxy.har.writeTo(harFile)
        println(proxy.har.log)

        // Collect all m3u8 http requests in this list
        val m3u8FilesList: MutableList<String> = ArrayList()
        val entries = proxy.har.log.entries
        var counter = 1

        println("entries.size::: " + entries.size)
        println(entries)

        for (entry in entries) {
            log.info("Request number: $counter")
            counter++
            log.info(entry.request.url)
            println("??? " + entry.request.url)
            if (entry.request.url.contains(".m3u8")) {
                println("???" + entry.request.url)
                m3u8FilesList.add(entry.request.url)
            }
        }

        log.info("HAR file created at >> $storeHAR")
        log.info("List of m3u8 http URLs found")
        m3u8FilesList.stream().forEach(System.out::println)
        driver.quit()
        proxy.stop()

        return m3u8FilesList
    }

}