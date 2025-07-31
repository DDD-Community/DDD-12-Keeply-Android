package com.keeply.data.core.di

import android.content.Context
import com.keeply.data.core.PreferencesRepositoryImpl
import com.keeply.data.core.datasource.PreferencesDataSource
import com.keeply.domain.repository.PreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    @Provides
    @Singleton
    fun providePreferencesDataSource(
        @ApplicationContext context: Context
    ): PreferencesDataSource = PreferencesDataSource(context)

    @Provides
    @Singleton
    fun providePreferencesRepository(
        dataSource: PreferencesDataSource
    ): PreferencesRepository = PreferencesRepositoryImpl(dataSource)
}