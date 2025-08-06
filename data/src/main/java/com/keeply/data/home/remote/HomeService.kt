package com.keeply.data.home.remote

import com.keeply.data.core.base.BaseResponse
import com.keeply.data.home.dto.HomeResponse
import retrofit2.http.GET

interface HomeService {
    @GET("api/home")
    suspend fun getHomeData(): BaseResponse<HomeResponse>
}