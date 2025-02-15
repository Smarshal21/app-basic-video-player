package com.pubscale.basicvideoplayer.domain.repository

interface VideoRepository {
    suspend fun fetchVideoUrl(): String
}