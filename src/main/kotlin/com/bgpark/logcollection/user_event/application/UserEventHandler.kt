package com.bgpark.logcollection.user_event.application

import com.bgpark.logcollection.user_event.controller.MessageProducer
import com.bgpark.logcollection.user_event.dto.UserEvent
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.*

@Service
class UserEventHandler(
    private val messageProducer: MessageProducer
) {
    suspend fun create(request: ServerRequest): ServerResponse {
        val userEvent = request.awaitBody<UserEvent>()
        println("userEvent: $userEvent")
        return ServerResponse.ok().json().bodyValueAndAwait(userEvent.uuid)
    }

    suspend fun createBulk(request: ServerRequest): ServerResponse {
        return ServerResponse.ok().json().bodyValueAndAwait("TODO")
    }
}