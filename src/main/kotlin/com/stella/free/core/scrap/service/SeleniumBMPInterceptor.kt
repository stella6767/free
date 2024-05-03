package com.stella.free.core.scrap.service




import com.browserup.bup.BrowserUpProxyServer
import com.browserup.bup.client.ClientUtil
import com.browserup.bup.proxy.CaptureType
import com.stella.free.global.util.logger
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.devtools.Command
import org.openqa.selenium.devtools.v124.network.Network
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.DesiredCapabilities
import java.io.File
import java.net.Inet4Address
import java.time.Duration
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.function.Consumer
import kotlin.collections.HashSet


class SeleniumBMPInterceptor {


    private val log = logger()

    fun retrieveM3U8requestFiles(url:String): List<String> {

        System.setProperty(
            "webdriver.chrome.driver",
            "/Users/stella6767/IdeaProjects/free/src/main/resources/static/drivers/chromedriver"
        )

        val latch = CountDownLatch(1)
        val m3u8FilesList = CopyOnWriteArrayList<String>()

        val options = ChromeOptions()
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.6367.91 Safari/537.36");

        val driver: ChromeDriver = ChromeDriver(options)
        val devTools = driver.devTools
        devTools.createSession()


        // Enable network tracking
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()))
        devTools.send(Command<Any>("Network.enable", mapOf()))


        // Add listener for network requests
        devTools.addListener(Network.requestWillBeSent(), Consumer { request ->
            val req = request.request
            //println("URL: " + req.url)
            //println("Method: " + req.method)
            if (req.url.contains(".m3u8")) {
                println("Found target URL, releasing latch");
                m3u8FilesList.add(req.url)
                latch.countDown();  // URL을 찾았을 때 래치 해제
            }
        })

//        devTools.addListener(Network.responseReceived()) { l: ResponseReceived ->
//            print("Response URL: ")
//            println(l.response.url)
//        }

        driver.get(url)

        val found = latch.await(5, TimeUnit.SECONDS) //찾을 때까지 대기
        if (found) {
            println("Target URL encountered, proceeding with further steps")
        } else {
            println("Timeout reached, target URL not encountered")
        }

        m3u8FilesList.forEach { println("확인==>$it") }
        driver.quit()

        return m3u8FilesList
    }




}