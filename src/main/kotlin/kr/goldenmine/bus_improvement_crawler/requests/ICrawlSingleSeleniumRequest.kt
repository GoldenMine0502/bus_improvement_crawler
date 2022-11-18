package kr.goldenmine.bus_improvement_crawler.requests

import org.openqa.selenium.WebDriver

interface ICrawlSingleSeleniumRequest: ICrawlRequest, ISeleniumRequest {
    val driver: WebDriver
}