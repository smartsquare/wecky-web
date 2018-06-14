package de.smartsquare.wecky.weckyweb

import com.amazonaws.services.lambda.AWSLambdaClient
import com.amazonaws.services.lambda.invoke.LambdaInvokerFactory
import de.smartsquare.wecky.weckyweb.crawl.CrawlService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LambdaConfig {

    @Value("\${cloud.aws.region.static}")
    val region: String? = null

    @Bean
    fun crawlService(): CrawlService = LambdaInvokerFactory.builder()
            .lambdaClient(AWSLambdaClient.builder().withRegion(region).build())
            .build(CrawlService::class.java)

    @Bean
    fun notifyService(): NotifyService = LambdaInvokerFactory.builder()
            .lambdaClient(AWSLambdaClient.builder().withRegion(region).build())
            .build(NotifyService::class.java)

}