package com.bgpark.logcollection.user_event.controller

interface MessageProducer {

    /**
     * 토픽에서 메세지 발행한다
     * @param topic 토픽
     * @param message 발행할 메세지
     */
    suspend fun send(topic: String, message: String)
}