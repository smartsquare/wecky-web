package de.smartsquare.wecky.weckyweb

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
class WeckyWebApplication {

    private val log = LoggerFactory.getLogger(WeckyWebApplication::class.java)

    @Bean
    fun init(repository: WebsiteRepository) = CommandLineRunner {
        // save a couple of customers
        repository.save(Website("HEISE", "https://www.heise.de"))

        // fetch all websites
        log.info("Websites found with findAll():")
        log.info("-------------------------------")
        repository.findAll().forEach { log.info(it.toString()) }
        log.info("")
    }

}

fun main(args: Array<String>) {
    runApplication<WeckyWebApplication>(*args)
}
