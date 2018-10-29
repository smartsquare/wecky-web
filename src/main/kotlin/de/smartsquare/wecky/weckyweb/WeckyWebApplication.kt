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
import java.util.*

@SpringBootApplication
@EnableScheduling
class WeckyWebApplication {

    companion object Factory {
        val log = LoggerFactory.getLogger(WeckyWebApplication::class.java.simpleName)
        val bootstrapKey = "BOOTSTRAP"
    }

    @Bean
    fun init(amazonDynamoDB: AmazonDynamoDB, userRepo: UserRepository, websiteRepo: WebsiteRepository) = CommandLineRunner {
        if (!bootstrap()) {
            return@CommandLineRunner
        }

        createTable(amazonDynamoDB, Website::class.java)
        createTable(amazonDynamoDB, User::class.java)
        val userId = UUID.randomUUID().toString()
        userRepo.save(User(userId, "Success Simulator", "success@simulator.amazonses.com"))
        websiteRepo.save(Website("TIME", "https://time.is", userId))

        // fetch all users
        log.info("Users found with findAll():")
        log.info("-------------------------------")
        userRepo.findAll().forEach { log.info(it.toString()) }
        log.info("")

        // fetch all websites
        log.info("Websites found with findAll():")
        log.info("-------------------------------")
        websiteRepo.findAll().forEach { log.info(it.toString()) }
        log.info("")
    }

    private fun bootstrap(): Boolean = (System.getenv(bootstrapKey)?.equals("true", true)
            ?: System.getProperty(bootstrapKey)?.equals("true", true)) ?: false

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
