package com.pubscale.basicvideoplayer.data.service

import com.pubscale.basicvideoplayer.data.models.VideoResponseDto
import retrofit2.http.GET

interface VideoApiService {
    @GET("refs/heads/main/video_url.json")
    suspend fun getVideoUrl(): VideoResponseDto
}