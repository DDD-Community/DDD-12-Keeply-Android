package com.keeply.data.core.di

import com.keeply.domain.repository.UserRepository
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

}