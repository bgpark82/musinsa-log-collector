package com.bgpark.logcollection.user_event.application

import com.bgpark.logcollection.user_event.controller.MessageProducer
import com.bgpark.logcollection.user_event.dto.LogMessage
import kotlinx.coroutines.delay
import org.springframework.stereotype.Component

@Component
class KafkaMessageProducer : MessageProducer {

    override suspend fun send(topic: String, message: String) {
        delay(1000)
        println("message has been sent: $topic, $message")
    }
}