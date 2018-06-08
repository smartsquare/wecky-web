package de.smartsquare.wecky.weckyweb

import com.amazonaws.regions.Regions
import com.amazonaws.services.lambda.AWSLambdaClient
import com.amazonaws.services.lambda.invoke.LambdaInvokerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class LambdaConfig {

    @Bean
    fun crawlService(): CrawlService {
        val client = AWSLambdaClient.builder().withRegion(Regions.EU_CENTRAL_1).build()
        return LambdaInvokerFactory.builder()
                .lambdaClient(client)
                .build(CrawlService::class.java)
    }
}