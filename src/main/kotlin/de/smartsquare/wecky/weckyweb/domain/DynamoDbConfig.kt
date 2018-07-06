package de.smartsquare.wecky.weckyweb.domain

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import org.slf4j.LoggerFactory
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
@EnableDynamoDBRepositories
(basePackages = arrayOf("de.smartsquare.wecky.weckyweb"))
class DynamoDbConfig {

    companion object Factory {
        val log = LoggerFactory.getLogger(DynamoDbConfig::class.java.simpleName)
    }

    @Value("\${amazon.dynamodb.endpoint}")
    val amazonDynamoDBEndpoint: String? = null

    @Value("\${amazon.aws.accesskey}")
    val amazonAWSAccessKey: String? = null

    @Value("\${amazon.aws.secretkey}")
    val amazonAWSSecretKey: String? = null

    @Value("\${cloud.aws.region.static}")
    val amazonAWSRegion: String? = null

    @Bean
    fun amazonDynamoDB(): AmazonDynamoDB {
        System.setProperty("aws.accessKeyId", amazonAWSAccessKey)
        System.setProperty("aws.secretKey", amazonAWSSecretKey)

        if (amazonDynamoDBEndpoint.isNullOrBlank()) {
            log.info("Using production DynamoDB endpoint [$amazonAWSRegion]")
            return AmazonDynamoDBClient.builder().withRegion(amazonAWSRegion).build()
        } else {
            log.info("Using local dev DynamoDB endpoint [$amazonDynamoDBEndpoint] [$amazonAWSRegion]")
            return AmazonDynamoDBClient.builder()
                    .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(amazonDynamoDBEndpoint, amazonAWSRegion))
                    .build()
        }
    }

    @Bean
    fun amazonAWSCredentials(): AWSCredentials {
        return BasicAWSCredentials(
                amazonAWSAccessKey!!, amazonAWSSecretKey!!)
    }
}