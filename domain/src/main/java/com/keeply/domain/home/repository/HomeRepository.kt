package com.keeply.domain.home.repository

import com.keeply.domain.home.model.HomeData
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getHomeData(): Flow<HomeData>
}