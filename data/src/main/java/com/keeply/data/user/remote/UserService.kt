package com.keeply.data.user.remote

import com.keeply.data.core.base.BaseResponse
import com.keeply.data.user.model.LoginKakaoRequest
import com.keeply.data.user.model.LogoutResponse
import com.keeply.data.user.model.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("api/login")
    suspend fun loginKakao(@Body kakao: LoginKakaoRequest): BaseResponse<TokenResponse>

    @POST("api/user/logout")
    suspend fun logout(): BaseResponse<LogoutResponse>

}