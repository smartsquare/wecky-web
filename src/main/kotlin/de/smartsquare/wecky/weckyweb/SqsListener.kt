package de.smartsquare.wecky.weckyweb

import org.slf4j.LoggerFactory
import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Service

@Service
class SqsListener {

    private val log = LoggerFactory.getLogger(SqsListener::class.java)

    @JmsListener(destination = "WeckyQueue")
    fun consumeMessage(msgJson: String) {
        log.info("Received SQS message: $msgJson")
    }
}