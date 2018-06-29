package de.smartsquare.wecky.weckyweb.sqs

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import de.smartsquare.wecky.weckyweb.domain.*
import de.smartsquare.wecky.weckyweb.notification.NotifyService
import org.slf4j.LoggerFactory
import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Service
import java.util.*


@Service
class SqsListener(
        val websiteRepository: WebsiteRepository,
        val userRepository: UserRepository,
        val notifyService: NotifyService) {

    private val log = LoggerFactory.getLogger(SqsListener::class.java)

    private val mapper = jacksonObjectMapper()

    @JmsListener(destination = "WeckyQueue")
    fun consumeMessage(msgJson: String) {
        log.info("Received SQS message: $msgJson")

        val typeRef = object : TypeReference<HashMap<String, String>>() {}
        val msgMap: Map<String, String> = mapper.readValue(msgJson, typeRef)
        val website = readWebsite(msgMap)
        val user = readUser(website)

        val notifyMsg = WebsiteChange(website.id, website.url, msgMap.get("content")!!, user.email)
        notifyService.notifyUser(notifyMsg)
    }

    private fun readUser(website: Website): User {
        val userId = website.userId
        val user = userRepository.findById(userId)
        return user.orElseThrow { IllegalArgumentException("No user with id [$userId]") }
    }

    private fun readWebsite(msgMap: Map<String, String>): Website {
        val id = msgMap.get("id")!!
        val website = websiteRepository.findById(id)
        return website.orElseThrow { IllegalArgumentException("No website with id [$id]") }
    }
}