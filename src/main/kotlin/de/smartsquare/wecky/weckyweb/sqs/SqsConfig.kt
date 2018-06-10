package de.smartsquare.wecky.weckyweb.sqs

import com.amazon.sqs.javamessaging.SQSConnectionFactory
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jms.annotation.EnableJms
import org.springframework.jms.config.DefaultJmsListenerContainerFactory
import org.springframework.jms.support.destination.DynamicDestinationResolver
import javax.jms.Session


@Configuration
@EnableJms
class SqsConfig {
    var connectionFactory = SQSConnectionFactory.builder()
            .withRegion(Region.getRegion(Regions.EU_CENTRAL_1))
            .withAWSCredentialsProvider(DefaultAWSCredentialsProviderChain())
            .build()


    @Bean
    fun jmsListenerContainerFactory(): DefaultJmsListenerContainerFactory {
        val factory = DefaultJmsListenerContainerFactory()
        factory.setConnectionFactory(this.connectionFactory)
        factory.setDestinationResolver(DynamicDestinationResolver())
        factory.setConcurrency("3-10")
        factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE)
        return factory
    }
}