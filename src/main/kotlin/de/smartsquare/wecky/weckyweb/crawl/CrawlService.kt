package de.smartsquare.wecky.weckyweb.crawl

import com.amazonaws.services.lambda.invoke.LambdaFunction
import com.amazonaws.services.lambda.model.InvocationType
import de.smartsquare.wecky.weckyweb.domain.Website

interface CrawlService {

    // InvocationType.Event not supported by sam-cli; use RequestResponse instead!
    @LambdaFunction(functionName = "CrawlWebsite", invocationType = InvocationType.Event)
    fun crawlWebsite(website: Website)
}