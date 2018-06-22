package de.smartsquare.wecky.weckyweb

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException
import de.smartsquare.wecky.weckyweb.domain.Website
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class WeckyWebApplication {

    private val log = LoggerFactory.getLogger(WeckyWebApplication::class.java)

    @Bean
    fun init(amazonDynamoDB: AmazonDynamoDB) = CommandLineRunner {
        val dynamoDBMapper = DynamoDBMapper(amazonDynamoDB)

        val tableRequest = dynamoDBMapper.generateCreateTableRequest(Website::class.java)
        tableRequest.provisionedThroughput = ProvisionedThroughput(1L, 1L)

        try {
            amazonDynamoDB.createTable(tableRequest)
        } catch (ex: ResourceInUseException) {
            //ignore existing table
        }
    }

}

fun main(args: Array<String>) {
    runApplication<WeckyWebApplication>(*args)
}
