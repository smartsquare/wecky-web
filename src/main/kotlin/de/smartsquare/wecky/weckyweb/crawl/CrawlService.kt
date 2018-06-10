package de.smartsquare.wecky.weckyweb.crawl

import com.amazonaws.services.lambda.invoke.LambdaFunction
import de.smartsquare.wecky.weckyweb.domain.Website

interface CrawlService {

    @LambdaFunction(functionName = "awscodestar-wecky-crawl-lambda-GetHelloWorld-1CFXZAZA30GHG")
    fun crawlWebsite(website: Website)
}