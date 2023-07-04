package com.bgpark.logcollection.user_event.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class UserEvent(
    @JsonProperty("uuid") val uuid: String
) {

}