package com.pubscale.basicvideoplayer.data.repository

import com.pubscale.basicvideoplayer.data.service.VideoDataSource
import com.pubscale.basicvideoplayer.domain.repository.VideoRepository
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val videoDataSource: VideoDataSource
) : VideoRepository {
    // Fetches video URL from API service
    override suspend fun fetchVideoUrl(): String {
        return videoDataSource.fetchVideoUrl()
    }
}
