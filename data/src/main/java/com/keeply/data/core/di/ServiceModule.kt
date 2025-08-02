package com.keeply.data.core.di

import com.keeply.data.screenshot.remote.OcrService
import com.keeply.data.folder.remote.FolderService
import com.keeply.data.image.remote.ImageService
import com.keeply.data.user.remote.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun provideOcrService(retrofit: Retrofit): OcrService {
        return retrofit.create(OcrService::class.java)
    }


    @Provides
    @Singleton
    fun provideFolderService(retrofit: Retrofit): FolderService {
        return retrofit.create(FolderService::class.java)
    }

    @Provides
    @Singleton
    fun provideImageService(retrofit: Retrofit): ImageService {
        return retrofit.create(ImageService::class.java)
    }
}