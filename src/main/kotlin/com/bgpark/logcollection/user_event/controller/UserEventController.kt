package com.bgpark.logcollection.user_event.controller

import com.bgpark.logcollection.user_event.dto.UserEvent
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user-event")
class UserEventController {

    @PostMapping
    fun create(@RequestBody userEvent: UserEvent) {
        println("user event: $userEvent")
    }

    @PostMapping("/bulk")
    fun createBulk(@RequestBody userEvents: List<UserEvent>) {
        println("user events: $userEvents")
    }

}