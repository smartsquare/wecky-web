package de.smartsquare.wecky.weckyweb.crawl

import de.smartsquare.wecky.weckyweb.domain.WebsiteRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CrawlController(val repository: WebsiteRepository, val crawlService: CrawlService) {

    @GetMapping("/crawl")
    fun crawl() {
        val websites = repository.findAll()
        websites.forEach({
            crawlService.crawlWebsite(it)
        })
    }
}