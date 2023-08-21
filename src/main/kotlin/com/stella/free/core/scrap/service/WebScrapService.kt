package com.stella.free.core.scrap.service

import org.jsoup.Jsoup
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.springframework.stereotype.Service
import java.time.Duration
import java.util.concurrent.TimeUnit


@Service
class WebScrapService(
    //private val driver: ChromeDriver
) {


    private fun getDriver(): ChromeDriver {

        System.setProperty("webdriver.chrome.driver", "path")

        val options = ChromeOptions()

        options.addArguments("--headless")
        options.addArguments("disable-gpu")
        options.addArguments("--disable-gpu")
        options.addArguments("lang=ko_KR")
        options.addArguments("window-size=1920x1080")
        options.addArguments("user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
        options.setCapability("ignoreProtectedModeSettings", true);

        return ChromeDriver(options)
    }


    fun <T> aspectDriver(url:String, function : () -> T) : T{

        openDriver(url)

        val invoke = function.invoke()

        getDriver().quit()

        return invoke
    }

    private fun openDriver(url:String){
        //10초 동안 페이지가 로드되기 기달, 그 전에 완료되면 진행
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10))
        getDriver().get(url) //chrome 열기
    }




    fun getPageSource(url: String) = aspectDriver(url) {

        val document =
            Jsoup.parse( getDriver().pageSource)

        val imgs =
            document.getElementsByTag("img").map { it.attr("src") }

        println(imgs)
    }




//    private fun getDataList(document: Document): List<String> {
//        val list: List<String> = ArrayList()
//        val selects: Elements = document.select(".sentence-list") //⭐⭐⭐
//        //select 메서드 안에 css selector를 작성하여 Elements를 가져올 수 있다.
//        for (select in selects) {
//            System.out.println(select.html()) //⭐⭐⭐
//            //html(), text(), children(), append().... 등 다양한 메서드 사용 가능
//            //https://jsoup.org/apidocs/org/jsoup/nodes/Element.html 참고
//        }
//        return list
//    }


}