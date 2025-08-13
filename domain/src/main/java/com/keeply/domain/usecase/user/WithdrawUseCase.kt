package com.keeply.domain.usecase.user

import com.keeply.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WithdrawUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Flow<Unit> {
        return userRepository.withdraw()
    }
}