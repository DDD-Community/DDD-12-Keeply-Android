package com.keeply.domain.repository

import com.keeply.domain.model.LoginKakao
import com.keeply.domain.model.Token
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun loginKakao(loginKakao: LoginKakao): Flow<Token>

    suspend fun saveRefreshToken(refreshToken: String)

    suspend fun saveAccessToken(accessToken: String)
    
    suspend fun fetchAccessToken(): String?
}