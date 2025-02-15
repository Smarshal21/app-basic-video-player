package com.pubscale.basicvideoplayer.di

import com.pubscale.basicvideoplayer.data.service.VideoApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://raw.githubusercontent.com/greedyraagava/test/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideVideoApi(retrofit: Retrofit): VideoApiService {
        return retrofit.create(VideoApiService::class.java)
    }
}
