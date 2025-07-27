package com.keeply.data.core.user.remote

import com.keeply.data.core.user.model.LoginKakaoRequest
import com.keeply.data.core.user.model.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("api/login")
    suspend fun loginKakao(@Body kakao: LoginKakaoRequest): TokenResponse

}