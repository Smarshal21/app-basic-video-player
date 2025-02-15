package com.pubscale.basicvideoplayer.data.service

interface VideoDataSource {
    suspend fun fetchVideoUrl(): String
}
