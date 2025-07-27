package com.keeply.data.user

import com.keeply.data.user.local.UserDataSource
import com.keeply.data.user.mapper.toDomain
import com.keeply.data.user.mapper.toRequest
import com.keeply.data.user.remote.UserService
import com.keeply.domain.model.LoginKakao
import com.keeply.domain.model.Token
import com.keeply.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    val userService: UserService,
    val userDataSource: UserDataSource,
) : UserRepository {
    override suspend fun loginKakao(loginKakao: LoginKakao): Flow<Token> = flow {
        emit(
            userService.loginKakao(
                loginKakao.copy(fcmToken = userDataSource.fetchFcmToken()).toRequest()
            ).response.toDomain()
        )
    }

    override suspend fun saveRefreshToken(refreshToken: String) =
        userDataSource.saveRefreshToken(refreshToken)


    override suspend fun saveAccessToken(accessToken: String) =
        userDataSource.saveAccessToken(accessToken)

}