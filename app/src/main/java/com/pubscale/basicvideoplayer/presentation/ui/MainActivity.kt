package com.pubscale.basicvideoplayer.presentation.ui

import android.app.PictureInPictureParams
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Rational
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.pubscale.basicvideoplayer.R
import com.pubscale.basicvideoplayer.presentation.states.VideoScreenState
import com.pubscale.basicvideoplayer.presentation.viewmodels.VideoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var player: ExoPlayer? = null

    // ViewModel for fetching video data
    private val videoViewModel: VideoViewModel by viewModels()
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progressBar)

        // Initialize ExoPlayer instance
        setupPlayer()

        // Observe changes in the UI state from ViewModel
        videoViewModel.uiState.observe(this) { state ->
            when (state) {
                is VideoScreenState.Loading -> {
                    progressBar.visibility = View.VISIBLE // Show loading indicator
                }
                is VideoScreenState.Success -> {
                    progressBar.visibility = View.GONE
                    playVideo(state.url) // Start video playback
                }
                is VideoScreenState.Error -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show() // Show error message
                }
            }
        }

        // Fetch video URL from ViewModel using coroutine
        lifecycleScope.launch {
            videoViewModel.fetchVideo()
        }
    }

    private fun setupPlayer() {
        // Create ExoPlayer instance and bind it to PlayerView
        player = ExoPlayer.Builder(this).build()
        findViewById<PlayerView>(R.id.player_view).player = player
    }

    private fun playVideo(url: String) {
        // Create a media item from the video URL and start playback
        val mediaItem = MediaItem.fromUri(Uri.parse(url))
        player?.setMediaItem(mediaItem)
        player?.prepare()
        player?.playWhenReady = true
    }

    override fun onPause() {
        super.onPause()
        // Enter Picture-in-Picture mode when the app is minimized
        if (player?.currentMediaItem != null && !isInPictureInPictureMode) {
            enterPiPMode()
        }
    }

    override fun onBackPressed() {
        // Enter Picture-in-Picture mode instead of exiting the app when back is pressed
        if (player?.currentMediaItem != null && !isInPictureInPictureMode) {
            enterPiPMode()
        } else {
            super.onBackPressed()
        }
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        // Enter Picture-in-Picture mode when user leaves the app
        if (player?.currentMediaItem != null) {
            enterPiPMode()
        }
    }

    private fun enterPiPMode() {
        // Check if device supports PiP mode (Android O and above) and enter PiP mode
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && player?.currentMediaItem != null) {
            val params = PictureInPictureParams.Builder()
                .setAspectRatio(Rational(16, 9)) // Maintain video aspect ratio
                .build()
            enterPictureInPictureMode(params)
        }
    }

    override fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode)
        // Resume playback when exiting PiP mode
        if (!isInPictureInPictureMode) player?.play()
    }
}
