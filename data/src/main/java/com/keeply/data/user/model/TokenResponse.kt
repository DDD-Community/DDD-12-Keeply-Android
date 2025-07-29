package com.keeply.data.user.model

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    val accessToken: String?,
    val refreshToken: String?
)