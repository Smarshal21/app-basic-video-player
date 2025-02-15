package com.pubscale.basicvideoplayer.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pubscale.basicvideoplayer.presentation.states.VideoScreenState
import com.pubscale.basicvideoplayer.domain.usecases.FetchVideoUrlUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(private val fetchVideoUrlUseCase: FetchVideoUrlUseCase) :
    ViewModel() {
    private val _uiState = MutableLiveData<VideoScreenState>()
    val uiState: LiveData<VideoScreenState> get() = _uiState

    //Fetches the video URL from the use case and updates the UI state accordingly.
    fun fetchVideo() {
        _uiState.value = VideoScreenState.Loading
        viewModelScope.launch {
            try {
                delay(2000) // Added this extra delay to see the loading state
                val url = fetchVideoUrlUseCase.execute() // Fetch video URL from use case
                _uiState.postValue(VideoScreenState.Success(url)) // Update state with URL
            } catch (e: Exception) {
                _uiState.postValue(VideoScreenState.Error("Error fetching video URL"))
            }
        }
    }
}