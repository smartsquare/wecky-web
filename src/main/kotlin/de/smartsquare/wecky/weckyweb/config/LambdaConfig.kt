package de.smartsquare.wecky.weckyweb.config

import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.lambda.AWSLambdaClient
import com.amazonaws.services.lambda.invoke.LambdaInvokerFactory
import de.smartsquare.wecky.weckyweb.crawl.CrawlService
import de.smartsquare.wecky.weckyweb.domain.DynamoDbConfig
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LambdaConfig {

    companion object Factory {
        val log = LoggerFactory.getLogger(LambdaConfig::class.java.simpleName)
    }

    @Value("\${cloud.aws.region.static}")
    val region: String? = null

    @Value("\${amazon.lambda.endpoint}")
    val amazonLambdaEndpoint: String? = null

    @Bean
    fun crawlService(): CrawlService {
        return if (amazonLambdaEndpoint.isNullOrBlank()) {
            log.info("Using production Lambda endpoint [$region]")
            LambdaInvokerFactory.builder()
                    .lambdaClient(AWSLambdaClient.builder().withRegion(region).build())
                    .build(CrawlService::class.java)
        } else {
            DynamoDbConfig.log.info("Using local dev Lambda endpoint [$amazonLambdaEndpoint] [$region]")
            LambdaInvokerFactory.builder()
                    .lambdaClient(AWSLambdaClient.builder()
                            .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(amazonLambdaEndpoint, region)).build())
                    .build(CrawlService::class.java)
        }
    }
}