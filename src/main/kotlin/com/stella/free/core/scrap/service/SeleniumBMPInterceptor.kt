package com.stella.free.core.scrap.service


import com.stella.free.global.util.logger
import net.lightbody.bmp.BrowserMobProxyServer
import net.lightbody.bmp.client.ClientUtil
import net.lightbody.bmp.core.har.Har
import net.lightbody.bmp.core.har.HarEntry
import net.lightbody.bmp.proxy.CaptureType
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.DesiredCapabilities
import java.io.File
import java.io.IOException
import java.net.Inet4Address
import java.net.UnknownHostException
import java.nio.file.Paths


class SeleniumBMPInterceptor {


    private val log = logger()

    fun retrieveM3U8requestFiles(websiteUrl: String): List<String> {

        log.info("Attempting to find m3u8 files in website >>> $websiteUrl")

        // Using Chrome as default browser.
        // For Support using Firefox or other browsers please download the corresponding driver
        System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver.exe")
        val proxy = BrowserMobProxyServer()

        // Does your website request require custom headers?
        // (Cookies, User-agent, jwt, etc) Add them here
        proxy.addRequestFilter { request, contents, messageInfo ->
            request.headers().add("Connection", "keep-alive")
            request.headers().add("Upgrade-Insecure-Requests", "1")
            request.headers().add(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.82 Safari/537.36"
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

        val seleniumProxy = ClientUtil.createSeleniumProxy(proxy)
        val hostIp = Inet4Address.getLocalHost().hostAddress
        seleniumProxy.setHttpProxy(hostIp + ":" + proxy.port)
        seleniumProxy.setSslProxy(hostIp + ":" + proxy.port)
        val seleniumCapabilities = DesiredCapabilities()
        seleniumCapabilities.setCapability(CapabilityType.PROXY, seleniumProxy)

        val options = ChromeOptions()
        options.addArguments("--ignore-certificate-errors")
        options.merge(seleniumCapabilities)


        //todo driver bean 으로 등록
        val driver: WebDriver = ChromeDriver(options)

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
        val har = proxy.har

        // Start capture
        driver[websiteUrl]
        driver.quit()
        proxy.stop()

        val storeHAR =
            Paths.get(".").toAbsolutePath().toUri().normalize().rawPath +
                    "HttpArchiveOutput/" + "trace-website-http-requests.har"

        val saveHarRequestFile =
            if (storeHAR.startsWith("\\") || storeHAR.startsWith("/")) {
                storeHAR.substring(1)
            } else {
                storeHAR
            }

        val harFile = File(saveHarRequestFile)
        har.writeTo(harFile)

        // Collect all m3u8 http requests in this list
        val m3u8FilesList: MutableList<String> = ArrayList()
        val entries = proxy.har.log.entries
        var counter = 1

        for (entry in entries) {
            log.info("Request number: $counter")
            counter++
            log.info(entry.request.url)
            if (entry.request.url.contains(".m3u8")) {
                m3u8FilesList.add(entry.request.url)
            }
        }

        log.info("HAR file created at >> $saveHarRequestFile")
        log.info("List of m3u8 http URLs found")
        m3u8FilesList.stream().forEach(System.out::println)

        return m3u8FilesList
    }

}