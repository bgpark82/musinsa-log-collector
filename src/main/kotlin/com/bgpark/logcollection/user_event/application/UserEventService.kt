package com.bgpark.logcollection.user_event.application

import com.bgpark.logcollection.user_event.controller.MessageProducer
import org.springframework.stereotype.Service

@Service
class UserEventService(
    private val messageProducer: MessageProducer
) {

}