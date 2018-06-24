package de.smartsquare.wecky.weckyweb.crawl

import com.amazonaws.services.lambda.invoke.LambdaFunction
import com.amazonaws.services.lambda.model.InvocationType.Event
import de.smartsquare.wecky.weckyweb.domain.Website

interface CrawlService {

    @LambdaFunction(functionName = "Wecky-CrawlWebsite", invocationType = Event)
    fun crawlWebsite(website: Website)

}