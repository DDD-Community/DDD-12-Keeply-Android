package com.keeply.data.core.di

import com.keeply.domain.repository.PreferencesRepository
import com.keeply.domain.repository.UserRepository
import com.keeply.domain.usecase.scan.GetScanOnBoardingVisibilityUseCase
import com.keeply.domain.usecase.scan.GetScanOnBoardingVisibilityUseCaseImpl
import com.keeply.domain.usecase.scan.SetDoNotShowDialogUseCase
import com.keeply.domain.usecase.scan.SetDoNotShowDialogUseCaseImpl
import com.keeply.domain.usecase.user.LoginKakaoUseCase
import com.keeply.domain.usecase.user.LoginKakaoUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideLoginKakaoUseCase(
        userRepository: UserRepository
    ): LoginKakaoUseCase = LoginKakaoUseCaseImpl(
        userRepository = userRepository
    )

    @Provides
    @Singleton
    fun provideGetScanOnBoardingVisibilityUseCase(
        preferencesRepository: PreferencesRepository
    ): GetScanOnBoardingVisibilityUseCase = GetScanOnBoardingVisibilityUseCaseImpl(
        repository = preferencesRepository
    )

    @Provides
    @Singleton
    fun provideSetDoNotShowScanOnBoarding(
        preferencesRepository: PreferencesRepository
    ): SetDoNotShowDialogUseCase = SetDoNotShowDialogUseCaseImpl(
        repository = preferencesRepository
    )

}