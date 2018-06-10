package de.smartsquare.wecky.weckyweb

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException
import de.smartsquare.wecky.weckyweb.domain.Website
import de.smartsquare.wecky.weckyweb.domain.WebsiteRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling
import java.lang.Exception

@SpringBootApplication
@EnableScheduling
class WeckyWebApplication {

    private val log = LoggerFactory.getLogger(WeckyWebApplication::class.java)

    @Bean
    fun init(amazonDynamoDB: AmazonDynamoDB, repository: WebsiteRepository) = CommandLineRunner {
        try {
            createTable(amazonDynamoDB)
            repository.save(Website("HEISE", "https://www.heise.de"))

            // fetch all websites
            log.info("Websites found with findAll():")
            log.info("-------------------------------")
            repository.findAll().forEach { log.info(it.toString()) }
            log.info("")
        } catch (ex: Exception) {
            log.error("Error initializing!", ex)
        }
    }

    private fun createTable(amazonDynamoDB: AmazonDynamoDB) {
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
