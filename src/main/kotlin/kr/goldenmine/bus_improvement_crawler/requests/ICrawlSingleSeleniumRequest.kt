package kr.goldenmine.bus_improvement_crawler.requests

import org.openqa.selenium.WebDriver

interface ICrawlSingleSeleniumRequest: ICrawlRequest {
    val driver: WebDriver
}