package com.bgpark.logcollection.user_event.application

import com.bgpark.logcollection.user_event.controller.MessageProducer
import com.bgpark.logcollection.user_event.dto.UserEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.*

@Service
class UserEventHandler(
    private val messageProducer: MessageProducer
) {
    suspend fun create(request: ServerRequest): ServerResponse {
        val userEvent = request.awaitBody<UserEvent>()

        CoroutineScope(Dispatchers.IO).launch {
            messageProducer.send("TOPIC", userEvent.uuid)
        }

        return ServerResponse.ok().json().bodyValueAndAwait("done")
    }

    suspend fun createBulk(request: ServerRequest): ServerResponse {
        return ServerResponse.ok().json().bodyValueAndAwait("TODO")
    }
}