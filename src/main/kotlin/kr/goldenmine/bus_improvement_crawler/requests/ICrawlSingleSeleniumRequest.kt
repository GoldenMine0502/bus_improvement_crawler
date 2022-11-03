package kr.goldenmine.bus_improvement_crawler.requests

import kr.goldenmine.bus_improvement_crawler.requests.bus_card_selenium.RequestBusCardSelenium
import org.openqa.selenium.WebDriver
import org.slf4j.Logger
import org.slf4j.LoggerFactory

interface ICrawlSingleSeleniumRequest: ICrawlRequest, ISeleniumRequest {
    val driver: WebDriver
}