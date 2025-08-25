package com.keeply.domain.usecase.user

import com.keeply.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface GetUserInfoUseCase {
    suspend operator fun invoke(): Flow<UserInfo>
}