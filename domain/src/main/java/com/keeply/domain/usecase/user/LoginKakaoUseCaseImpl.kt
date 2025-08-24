package com.keeply.domain.usecase.user

import com.keeply.domain.model.LoginKakao
import com.keeply.domain.model.Token
import com.keeply.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class LoginKakaoUseCaseImpl @Inject constructor(
    val userRepository: UserRepository
): LoginKakaoUseCase{
    override suspend fun invoke(loginKakao: LoginKakao): Flow<Token> =
        userRepository.loginKakao(loginKakao).onEach { token ->
            userRepository.saveAccessToken(token.accessToken)
            userRepository.saveRefreshToken(token.refreshToken)
            loginKakao.kakao_account?.email?.let { userRepository.saveUserEmail(it) }
            loginKakao.kakao_account?.profile?.nickname?.let { userRepository.saveUserNickname(it) }
            loginKakao.kakao_account?.profile?.profile_image_url?.let { userRepository.saveUserImage(it) }
        }

}