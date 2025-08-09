package com.keeply.data.core.di

import com.keeply.data.screenshot.OcrRepositoryImpl
import com.keeply.data.folder.FolderRepositoryImpl
import com.keeply.data.image.ImageRepositoryImpl
import com.keeply.data.home.repository.HomeRepositoryImpl
import com.keeply.data.user.UserRepositoryImpl
import com.keeply.domain.repository.OcrRepository
import com.keeply.domain.folder.repository.FolderRepository
import com.keeply.domain.image.repository.ImageRepository
import com.keeply.domain.home.repository.HomeRepository
import com.keeply.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    abstract fun bindOcrRepository(
        ocrRepositoryImpl: OcrRepositoryImpl
    ): OcrRepository


    @Binds
    abstract fun bindFolderRepository(
        folderRepositoryImpl: FolderRepositoryImpl
    ): FolderRepository

    @Binds
    abstract fun bindImageRepository(
        imageRepositoryImpl: ImageRepositoryImpl
    ): ImageRepository

    @Binds
    abstract fun bindHomeRepository(
        homeRepositoryImpl: HomeRepositoryImpl
    ): HomeRepository
}