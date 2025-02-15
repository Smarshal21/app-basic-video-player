package com.pubscale.basicvideoplayer.data.service

import com.pubscale.basicvideoplayer.domain.repository.VideoRepository
import javax.inject.Inject

class VideoDataSourceImpl @Inject constructor(
    private val videoRepository: VideoRepository
) : VideoDataSource{
    override suspend fun fetchVideoUrl(): String {
        return videoRepository.fetchVideoUrl()
    }
}