package de.smartsquare.wecky.weckyweb.sqs

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import de.smartsquare.wecky.weckyweb.NotifyService
import de.smartsquare.wecky.weckyweb.domain.Website
import de.smartsquare.wecky.weckyweb.domain.WebsiteRepository
import org.slf4j.LoggerFactory
import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Service
import java.util.*


@Service
class SqsListener(val websiteRepository: WebsiteRepository, val notifyService: NotifyService) {

    private val log = LoggerFactory.getLogger(SqsListener::class.java)

    private val mapper = jacksonObjectMapper()

    @JmsListener(destination = "WeckyQueue")
    fun consumeMessage(msgJson: String) {
        log.info("Received SQS message: $msgJson")
        val website = readWebsite(msgJson)
        notifyService.notifyUser(website)
    }

    private fun readWebsite(msgJson: String): Website {
        val typeRef = object : TypeReference<HashMap<String, String>>() {}
        val msgMap: Map<String, String> = mapper.readValue(msgJson, typeRef)

        val website = websiteRepository.findByUrl(msgMap.get("url")!!)
        return website.iterator().next()
    }
}