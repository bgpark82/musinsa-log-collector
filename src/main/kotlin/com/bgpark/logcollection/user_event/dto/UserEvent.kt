package com.bgpark.logcollection.user_event.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class UserEvent(
    @JsonProperty("data") val data: Map<String, Any>
) {

}