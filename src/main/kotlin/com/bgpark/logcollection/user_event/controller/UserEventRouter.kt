package com.bgpark.logcollection.user_event.controller

import com.bgpark.logcollection.common.LOG_PATH
import com.bgpark.logcollection.common.USER_EVENT_PATH
import com.bgpark.logcollection.user_event.application.UserEventHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.coRouter


@Configuration
class UserEventRouter {

    @Bean
    fun router(userEventHandler: UserEventHandler): RouterFunction<ServerResponse> {

        /**
         * coRouter : 라우팅을 정의한 함수
         * @see https://docs.spring.io/spring-framework/reference/languages/kotlin/coroutines.html
         * @see https://docs.spring.io/spring-framework/docs/current/kdoc-api/spring-webflux/org.springframework.web.reactive.function.server/co-router.html
         */
        return coRouter {
            LOG_PATH.nest {
                accept(MediaType.APPLICATION_JSON).nest {
                    USER_EVENT_PATH.nest {
                        POST("", userEventHandler::create)
                        POST("/bulk", userEventHandler::create)
                    }
                }
            }
        }
    }
}