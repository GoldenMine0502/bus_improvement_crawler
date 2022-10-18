package kr.goldenmine.bus_improvement_crawler.requests

interface ICrawlRetrofitRequest<T> : ICrawlRequest {
    val service: T
}