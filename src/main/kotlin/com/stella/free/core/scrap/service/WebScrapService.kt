package com.stella.free.core.scrap.service

import org.jsoup.Jsoup
import org.openqa.selenium.chrome.ChromeDriver
import org.springframework.stereotype.Service


@Service
class WebScrapService(
    private val driver: ChromeDriver
) {


    private fun openDriver(url:String){
        driver.get(url)         //chrome 열기
    }


    fun getPageSource(url: String){

        openDriver(url)

        val document =
            Jsoup.parse(driver.pageSource)


        val imgs =
            document.getElementsByTag("img").map { it.attr("src") }


        println("!!!")
        println(imgs)


        driver.quit()

    }




}