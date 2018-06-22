package de.smartsquare.wecky.weckyweb

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException
import de.smartsquare.wecky.weckyweb.domain.User
import de.smartsquare.wecky.weckyweb.domain.UserRepository
import de.smartsquare.wecky.weckyweb.domain.Website
import de.smartsquare.wecky.weckyweb.domain.WebsiteRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling
import java.lang.Exception
import java.util.*

@SpringBootApplication
@EnableScheduling
class WeckyWebApplication {

    private val log = LoggerFactory.getLogger(WeckyWebApplication::class.java)

    @Bean
    fun init(amazonDynamoDB: AmazonDynamoDB, userRepo: UserRepository, websiteRepo: WebsiteRepository) = CommandLineRunner {
        try {
            createTable(amazonDynamoDB, Website::class.java)
            createTable(amazonDynamoDB, User::class.java)
            val userId = UUID.randomUUID().toString()
            userRepo.save(User(userId, "Max Musterman", "max@muster.man"))
            websiteRepo.save(Website("HEISE", "https://www.heise.de", userId))

            // fetch all websites
            log.info("Websites found with findAll():")
            log.info("-------------------------------")
            websiteRepo.findAll().forEach { log.info(it.toString()) }
            log.info("")
        } catch (ex: Exception) {
            log.error("Error initializing!", ex)
        }
    }

    private fun createTable(amazonDynamoDB: AmazonDynamoDB, clazz: Class<out Any>) {
        val dynamoDBMapper = DynamoDBMapper(amazonDynamoDB)

        val tableRequest = dynamoDBMapper.generateCreateTableRequest(clazz)
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
