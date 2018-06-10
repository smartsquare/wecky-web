package de.smartsquare.wecky.weckyweb.crawl

import de.smartsquare.wecky.weckyweb.domain.WebsiteRepository
import org.springframework.beans.factory.annotation.Autowired
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class CrawlScheduler(
        @Autowired
        val repository: WebsiteRepository,
        @Autowired
        val crawlService: CrawlService) {

    val executor: ScheduledExecutorService = Executors.newScheduledThreadPool(1)

    fun runCrawlers() {
        executor.schedule({
            val websites = repository.findAll()
            websites.forEach({
                crawlService.crawlWebsite(it)
            })
        }, 1, TimeUnit.HOURS)
    }
}