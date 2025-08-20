package com.keeply.domain.usecase.user

import com.keeply.domain.model.NotificationSetting
import com.keeply.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotificationSettingUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Flow<NotificationSetting> {
        return userRepository.getNotificationSetting()
    }
}