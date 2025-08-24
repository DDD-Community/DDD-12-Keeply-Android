package com.keeply.domain.usecase.user

import com.keeply.domain.model.NotificationSetting
import com.keeply.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateNotificationSettingUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(notificationSetting: NotificationSetting): Flow<NotificationSetting> {
        return userRepository.updateNotificationSetting(notificationSetting)
    }
}