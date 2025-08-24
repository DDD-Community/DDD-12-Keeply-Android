package com.keeply.data.user.mapper

import com.keeply.data.user.model.NotificationSettingRequest
import com.keeply.data.user.model.NotificationSettingResponse
import com.keeply.domain.model.NotificationSetting

fun NotificationSetting.toRequest(): NotificationSettingRequest {
    return NotificationSettingRequest(
        allowStorageNotification = allowStorageNotification,
        allowMarketingNotification = allowMarketingNotification
    )
}

fun NotificationSettingResponse?.toDomain(): NotificationSetting {
    return NotificationSetting(
        allowStorageNotification = this?.allowStorageNotification ?: false,
        allowMarketingNotification = this?.allowMarketingNotification ?: false
    )
}