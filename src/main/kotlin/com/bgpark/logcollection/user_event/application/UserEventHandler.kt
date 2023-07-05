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
        val message = attachExtraInfo(request)

        CoroutineScope(Dispatchers.IO).launch {
            messageProducer.send("TOPIC", message.toString())
        }

        return ServerResponse.ok().json().bodyValueAndAwait("done")
    }

    suspend fun createBulk(request: ServerRequest): ServerResponse {
        return ServerResponse.ok().json().bodyValueAndAwait("TODO")
    }


    /**
     * http header에서 필요한 정보를 추출하여 payload에 추가
     *
     * @param request http 요청
     * @return payload
     */
    private suspend fun attachExtraInfo(request: ServerRequest): Map<String, Any> {
        val event = request.awaitBody<UserEvent>()
        println("request : ${event}")
        println("request body : ${event.data}")

        val result = mutableMapOf<String, Any>()
        result.putAll(event.data)

        addUserAgentHeader(request, result)
        return result
    }

    /**
     * User-Agent 헤더값을 결과로 반환
     * User-Agent : 요청을 보낸 브라우저의 종류와 버전 확인
     * @see https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/User-Agent
     */
    private fun addUserAgentHeader(
        request: ServerRequest,
        result: MutableMap<String, Any>
    ) {
        val userAgentHeader = request.headers().header("User-Agent")

        result["userAgent"] =
            when (userAgentHeader.size) {
                1 -> userAgentHeader[0]
                else -> "Unknown"
            }
    }
}