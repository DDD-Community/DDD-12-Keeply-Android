package com.keeply.data.user

import com.keeply.data.user.local.UserDataSource
import com.keeply.data.user.mapper.toDomain
import com.keeply.data.user.mapper.toRequest
import com.keeply.data.user.remote.UserService
import com.keeply.domain.model.LoginKakao
import com.keeply.domain.model.NotificationSetting
import com.keeply.domain.model.Token
import com.keeply.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
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

    override suspend fun logout(): Flow<Unit> = flow {
        try {
            val response = userService.logout()
            if (response.success == true) {
                clearTokens()
                emit(Unit)
            } else {
                throw Exception(response.reason ?: "로그아웃에 실패했습니다")
            }
        } catch (e: HttpException) {
            throw Exception("네트워크 오류가 발생했습니다: ${e.message}")
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun withdraw(): Flow<Unit> = flow {
        try {
            val response = userService.withdraw()
            if (response.success == true) {
                clearTokens()
                emit(Unit)
            } else {
                throw Exception(response.reason ?: "회원 탈퇴에 실패했습니다")
            }
        } catch (e: HttpException) {
            throw Exception("네트워크 오류가 발생했습니다: ${e.message}")
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun saveRefreshToken(refreshToken: String) =
        userDataSource.saveRefreshToken(refreshToken)


    override suspend fun saveAccessToken(accessToken: String) =
        userDataSource.saveAccessToken(accessToken)
    
    override suspend fun fetchAccessToken(): String? = 
        userDataSource.fetchAccessToken()

    override suspend fun clearTokens() {
        userDataSource.clearAccessToken()
        userDataSource.clearRefreshToken()
    }

    override suspend fun saveUserEmail(email: String) =
        userDataSource.saveUserEmail(email)

    override suspend fun saveUserNickname(nickname: String) =
        userDataSource.saveUserNickname(nickname)

    override suspend fun saveUserImage(image: String) =
        userDataSource.saveUserImage(image)

    override suspend fun getUserEmail(): String? =
        userDataSource.getUserEmail()

    override suspend fun getUserNickname(): String? =
        userDataSource.getUserNickname()

    override suspend fun getUserImage(): String? =
        userDataSource.getUserImage()

    override suspend fun getNotificationSetting(): Flow<NotificationSetting> = flow {
        try {
            val response = userService.getNotificationSetting()
            if (response.success == true) {
                emit(response.response.toDomain())
            } else {
                throw Exception(response.reason ?: "알림 설정 조회에 실패했습니다")
            }
        } catch (e: HttpException) {
            throw Exception("네트워크 오류가 발생했습니다: ${e.message}")
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun updateNotificationSetting(notificationSetting: NotificationSetting): Flow<NotificationSetting> = flow {
        try {
            val response = userService.updateNotificationSetting(notificationSetting.toRequest())
            if (response.success == true) {
                emit(response.response.toDomain())
            } else {
                throw Exception(response.reason ?: "알림 설정 업데이트에 실패했습니다")
            }
        } catch (e: HttpException) {
            throw Exception("네트워크 오류가 발생했습니다: ${e.message}")
        } catch (e: Exception) {
            throw e
        }
    }

}