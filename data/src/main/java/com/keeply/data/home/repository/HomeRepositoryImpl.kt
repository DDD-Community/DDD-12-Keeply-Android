package com.keeply.data.home.repository

import com.keeply.data.home.mapper.toDomain
import com.keeply.data.home.remote.HomeService
import com.keeply.domain.home.model.HomeData
import com.keeply.domain.home.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeService: HomeService
) : HomeRepository {
    
    override suspend fun getHomeData(): Flow<HomeData> = flow {
        try {
            val response = homeService.getHomeData()
            if (response.success == true) {
                response.response?.let { data ->
                    emit(data.toDomain())
                } ?: throw Exception("홈 데이터를 불러올 수 없습니다")
            } else {
                throw Exception(response.reason ?: "홈 데이터를 불러올 수 없습니다")
            }
        } catch (e: HttpException) {
            throw Exception("네트워크 오류가 발생했습니다: ${e.message}")
        } catch (e: Exception) {
            throw e
        }
    }
}