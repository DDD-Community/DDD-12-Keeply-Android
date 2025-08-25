package com.keeply.domain.usecase.user

import com.keeply.domain.model.UserInfo
import com.keeply.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserInfoUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : GetUserInfoUseCase {
    override suspend fun invoke(): Flow<UserInfo> = flow {
        val email = userRepository.getUserEmail()
        val nickname = userRepository.getUserNickname()
        val image = userRepository.getUserImage()
        
        emit(UserInfo(
            email = email,
            nickname = nickname,
            image = image
        ))
    }
}