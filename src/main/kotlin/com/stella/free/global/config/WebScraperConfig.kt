package com.stella.free.global.config

import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


//@Configuration
class WebScraperConfig(

) {

    @Value("\${chrome.driver}")
    private lateinit var driverPath: String


    //@Bean
    fun chromeDriver(): ChromeDriver {

        System.setProperty("webdriver.chrome.driver", driverPath)

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


}