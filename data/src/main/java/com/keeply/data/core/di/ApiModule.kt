package com.keeply.data.core.di

import com.keeply.data.BuildConfig
import com.keeply.data.core.interceptor.AuthInterceptor
import com.keeply.data.user.local.UserDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApiModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
    }

    @Singleton
    @Provides
    fun provideAuthInterceptor(
        userDataSource: UserDataSource
    ): AuthInterceptor = AuthInterceptor(userDataSource)

    @Singleton
    @Provides
    fun provideOKHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(authInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, json: Json): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)
            .build()
    }
}