package com.keeply.data.core.user.model

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    val success: Boolean,
    val reason: String,
    val response: Response
) {
    @Serializable
    data class Response(
        val accessToken: String?,
        val refreshToken: String?
    )
}
