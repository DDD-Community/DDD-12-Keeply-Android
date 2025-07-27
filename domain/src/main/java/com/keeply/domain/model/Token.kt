package com.keeply.domain.model

data class Token(
    val success: Boolean,
    val reason: String,
    val response: Response
) {
    data class Response(
        val accessToken: String,
        val refreshToken: String
    )
}