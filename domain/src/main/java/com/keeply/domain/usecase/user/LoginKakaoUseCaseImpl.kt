package com.keeply.domain.usecase.user

import com.keeply.domain.model.LoginKakao
import com.keeply.domain.model.Token
import com.keeply.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginKakaoUseCaseImpl @Inject constructor(
    val userRepository: UserRepository
): LoginKakaoUseCase{
    override suspend fun invoke(loginKakao: LoginKakao): Flow<Token> =
        userRepository.loginKakao(loginKakao)

}