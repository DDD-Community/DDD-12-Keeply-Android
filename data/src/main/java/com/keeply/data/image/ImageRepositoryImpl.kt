package com.keeply.data.image

import com.keeply.data.core.extension.toException
import com.keeply.data.dto.image.CreateImageRequest
import com.keeply.data.image.mapper.toDomain
import com.keeply.data.image.remote.ImageService
import com.keeply.domain.image.model.Image
import com.keeply.domain.image.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val imageService: ImageService,
    private val json: Json
) : ImageRepository {
    
    override suspend fun createImage(
        isCached: Boolean,
        cachedImageId: String,
        imageId: Long,
        imageInsight: String,
        folderId: Long,
        tag: String
    ): Flow<Image> = flow {
        try {
            val response = imageService.createImage(
                CreateImageRequest(
                    isCached = isCached,
                    cachedImageId = cachedImageId,
                    imageId = imageId,
                    imageInsight = imageInsight,
                    folderId = folderId,
                    tag = tag
                )
            )
            
            if (response.success == true && response.response != null) {
                emit(response.response.toDomain())
            } else {
                throw Exception(response.reason)
            }
        } catch (e: HttpException) {
            throw e.toException(json)
        }
    }
}