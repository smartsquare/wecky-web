package de.smartsquare.wecky.weckyweb.crawl

import com.amazonaws.services.lambda.invoke.LambdaFunction
import de.smartsquare.wecky.weckyweb.domain.Website

interface CrawlService {

    @LambdaFunction(functionName = "Wecky-CrawlWebsite")
    fun crawlWebsite(website: Website)
}