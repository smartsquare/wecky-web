package de.smartsquare.wecky.weckyweb

import com.amazonaws.services.lambda.AWSLambdaClientBuilder
import com.amazonaws.services.lambda.invoke.LambdaInvokerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class LambdaConfig {

    @Bean
    fun crawlService(): CrawlService {
        return LambdaInvokerFactory.builder()
                .lambdaClient(AWSLambdaClientBuilder.defaultClient())
                .build(CrawlService::class.java)
    }
}