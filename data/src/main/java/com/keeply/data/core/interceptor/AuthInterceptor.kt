package com.keeply.data.core.interceptor

import com.keeply.data.user.local.UserDataSource
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val userDataSource: UserDataSource
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        
        val accessToken = runBlocking {
            userDataSource.fetchAccessToken()
        }
        
        val request = if (accessToken != null) {
            originalRequest.newBuilder()
                .header("Authorization", "Bearer $accessToken")
                .build()
        } else {
            originalRequest
        }
        
        return chain.proceed(request)
    }
}