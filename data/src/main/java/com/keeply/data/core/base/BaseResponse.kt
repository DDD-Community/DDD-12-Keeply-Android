package com.keeply.data.core.base

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T> (
    val success: Boolean?,
    val reason: String?,
    val response: T? = null,
)