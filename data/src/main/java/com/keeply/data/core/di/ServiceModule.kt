package com.keeply.data.core.di

import com.keeply.data.folder.remote.FolderService
import com.keeply.data.home.remote.HomeService
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
    fun provideFolderService(retrofit: Retrofit): FolderService {
        return retrofit.create(FolderService::class.java)
    }
    
    @Provides
    @Singleton
    fun provideHomeService(retrofit: Retrofit): HomeService {
        return retrofit.create(HomeService::class.java)
    }
}