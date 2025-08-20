package com.keeply.data.user.model

import kotlinx.serialization.Serializable

@Serializable
data class NotificationSettingResponse(
    val allowStorageNotification: Boolean,
    val allowMarketingNotification: Boolean
)