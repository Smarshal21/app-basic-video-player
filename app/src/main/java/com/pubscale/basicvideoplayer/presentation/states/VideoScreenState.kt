package com.pubscale.basicvideoplayer.presentation.states

sealed class VideoScreenState {
    object Loading : VideoScreenState()
    data class Success(val url: String) : VideoScreenState()
    data class Error(val message: String) : VideoScreenState()
}
