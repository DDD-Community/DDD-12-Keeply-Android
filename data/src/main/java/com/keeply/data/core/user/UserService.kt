package com.keeply.data.core.user

import com.keeply.data.core.user.model.LoginKakaoRequest
import com.keeply.data.core.user.model.TokenResponse
import retrofit2.http.POST

interface UserService {

    @POST("api/login")
    fun loginKakao(kakao: LoginKakaoRequest): TokenResponse

}