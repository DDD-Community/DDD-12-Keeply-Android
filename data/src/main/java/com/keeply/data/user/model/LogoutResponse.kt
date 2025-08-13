package com.keeply.data.user.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LogoutResponse(
    val message: String
)