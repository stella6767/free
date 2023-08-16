package com.stella.free.core.scrap.service

import org.jsoup.Jsoup
import org.openqa.selenium.chrome.ChromeDriver
import org.springframework.stereotype.Service
import java.time.Duration
import java.util.concurrent.TimeUnit


@Service
class WebScrapService(
    private val driver: ChromeDriver
) {


    fun <T> aspectDriver(url:String, function : () -> T) : T{

        openDriver(url)

        val invoke = function.invoke()

        driver.quit()

        return invoke
    }

    private fun openDriver(url:String){
        //10초 동안 페이지가 로드되기 기달, 그 전에 완료되면 진행
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))
        driver.get(url) //chrome 열기
    }




    fun getPageSource(url: String) = aspectDriver(url) {

        val document =
            Jsoup.parse(driver.pageSource)

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