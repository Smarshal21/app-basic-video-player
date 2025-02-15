package com.pubscale.basicvideoplayer.domain.usecases

import com.pubscale.basicvideoplayer.data.service.VideoDataSource
import javax.inject.Inject

class FetchVideoUrlUseCase @Inject constructor(private val videoDataSource: VideoDataSource) {
    suspend fun execute(): String {
        return videoDataSource.fetchVideoUrl()
    }
}
