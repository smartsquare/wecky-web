package de.smartsquare.wecky.weckyweb

import com.amazonaws.services.lambda.invoke.LambdaFunction

interface CrawlService {

    @LambdaFunction(functionName = "awscodestar-wecky-crawl-lambda-GetHelloWorld-1CFXZAZA30GHG")
    fun crawlWebsite(website: Website)
}