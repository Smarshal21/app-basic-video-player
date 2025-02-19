package com.pubscale.basicvideoplayer.domain.usecases

import com.pubscale.basicvideoplayer.domain.repository.VideoRepository
import javax.inject.Inject

class FetchVideoUrlUseCase @Inject constructor(private val videoRepository: VideoRepository) {
    suspend fun execute(): String {
        return videoRepository.fetchVideoUrl()
    }
}
