package com.bgpark.logcollection.user_event.application

import com.bgpark.logcollection.user_event.controller.MessageProducer
import com.bgpark.logcollection.user_event.dto.LogMessage
import org.springframework.stereotype.Component

@Component
class KafkaMessageProducer : MessageProducer {

    override suspend fun send(topic: String, message: LogMessage) {
        TODO("Not yet implemented")
    }
}