package com.keeply.data.core.user.model

data class TokenResponse(
    val success: Boolean,
    val reason: String,
    val response: Response
) {
    data class Response(
        val accessToken: String?,
        val refreshToken: String?
    )
}
