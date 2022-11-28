package kr.goldenmine.bus_improvement_crawler.requests

import org.openqa.selenium.WebDriver

interface ICrawlSingleSeleniumRequest: ISeleniumRequest {
    val driver: WebDriver
}