package com.bgpark.logcollection.user_event.controller

import com.bgpark.logcollection.user_event.application.SearchEventHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class SearchEventRouterConfig {

    @Bean
    fun searchEventRouter(searchEventHandler: SearchEventHandler) = coRouter {
        "/log".nest {
            accept(MediaType.APPLICATION_JSON).nest {
                "/search-event".nest {
                    POST("", searchEventHandler::create)
                }
            }
        }
    }
}