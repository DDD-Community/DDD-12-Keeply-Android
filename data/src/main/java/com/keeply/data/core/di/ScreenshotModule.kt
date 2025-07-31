package com.keeply.data.core.di

import android.content.Context
import com.keeply.data.screenshot.ScreenshotRepositoryImpl
import com.keeply.domain.repository.LocalScreenshotRepository
import com.keeply.domain.usecase.scan.GetLocalScreenshotsUseCase
import com.keeply.domain.usecase.scan.GetLocalScreenshotsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ScreenshotModule {

    @Provides
    fun provideScreenshotRepository(
        @ApplicationContext context: Context
    ): LocalScreenshotRepository = ScreenshotRepositoryImpl(context.contentResolver)

    @Provides
    fun provideGetScreenshotsUseCase(
        repository: LocalScreenshotRepository
    ): GetLocalScreenshotsUseCase = GetLocalScreenshotsUseCaseImpl(repository)
}