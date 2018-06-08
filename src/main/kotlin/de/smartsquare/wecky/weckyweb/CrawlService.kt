package de.smartsquare.wecky.weckyweb

import com.amazonaws.services.lambda.invoke.LambdaFunction

interface CrawlService {

    @LambdaFunction(functionName = "wecky-crawler")
    fun crawlWebsite(website: Website)
}