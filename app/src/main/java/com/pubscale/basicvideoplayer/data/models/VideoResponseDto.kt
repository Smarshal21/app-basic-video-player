package com.pubscale.basicvideoplayer.data.models

import com.google.gson.annotations.SerializedName

data class VideoResponseDto(
    @SerializedName("url") val videoUrl: String,
)

