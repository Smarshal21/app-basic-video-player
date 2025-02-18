package com.pubscale.basicvideoplayer.data.service

import javax.inject.Inject

class VideoDataSourceImpl @Inject constructor(
    private val apiService: VideoApiService
) : VideoDataSource{
    override suspend fun fetchVideoUrl(): String {
        return apiService.getVideoUrl().videoUrl
    }
}