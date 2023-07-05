package com.bgpark.logcollection.user_event.application

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.buildAndAwait

@Service
class SearchEventHandler(
    private val messageProducer: KafkaMessageProducer
) {
    suspend fun create(request: ServerRequest): ServerResponse {
        val message = request.awaitBody<String>()
        // TODO: 왜 Coroutine context에 Dispatcher.IO가 들어갔지??
        CoroutineScope(Dispatchers.IO).launch {
            messageProducer.send("TOPIC", message)
        }
        return ServerResponse.ok().buildAndAwait()
    }
}