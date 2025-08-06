package com.keeply.domain.home.usecase

import com.keeply.domain.home.model.HomeData
import com.keeply.domain.home.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHomeDataUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(): Flow<HomeData> {
        return homeRepository.getHomeData()
    }
}