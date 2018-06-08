package de.smartsquare.wecky.weckyweb

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.util.StringUtils


@Configuration
@EnableDynamoDBRepositories
(basePackages = arrayOf("de.smartsquare.wecky.weckyweb"))
class DynamoDbConfig {
    @Value("\${amazon.dynamodb.endpoint}")
    val amazonDynamoDBEndpoint: String? = null

    @Value("\${amazon.aws.accesskey}")
    val amazonAWSAccessKey: String? = null

    @Value("\${amazon.aws.secretkey}")
    val amazonAWSSecretKey: String? = null

    @Bean
    fun amazonDynamoDB(): AmazonDynamoDB {
        val amazonDynamoDB = AmazonDynamoDBClient(amazonAWSCredentials())

        if (!StringUtils.isEmpty(amazonDynamoDBEndpoint)) {
            amazonDynamoDB.setEndpoint(amazonDynamoDBEndpoint)
        }

        return amazonDynamoDB
    }

    @Bean
    fun amazonAWSCredentials(): AWSCredentials {
        return BasicAWSCredentials(
                amazonAWSAccessKey!!, amazonAWSSecretKey!!)
    }
}