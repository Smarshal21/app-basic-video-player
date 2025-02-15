package com.pubscale.basicvideoplayer.di

import com.pubscale.basicvideoplayer.data.repository.VideoRepositoryImpl
import com.pubscale.basicvideoplayer.data.service.VideoApiService
import com.pubscale.basicvideoplayer.data.service.VideoDataSource
import com.pubscale.basicvideoplayer.data.service.VideoDataSourceImpl
import com.pubscale.basicvideoplayer.domain.repository.VideoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideVideoDataSource(videoRepository: VideoRepository): VideoDataSource {
        return VideoDataSourceImpl(videoRepository)
    }

    @Provides
    @Singleton
    fun provideVideoRepository(apiService: VideoApiService): VideoRepository {
        return VideoRepositoryImpl(apiService)
    }
}

