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
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.pubscale.basicvideoplayer.R
import com.pubscale.basicvideoplayer.presentation.states.VideoScreenState
import com.pubscale.basicvideoplayer.presentation.viewmodels.VideoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var player: ExoPlayer? = null
    private val videoViewModel: VideoViewModel by viewModels()
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progressBar)
        setupPlayer()

        videoViewModel.uiState.observe(this) { state ->
            when (state) {
                is VideoScreenState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
                is VideoScreenState.Success -> {
                    progressBar.visibility = View.GONE
                    playVideo(state.url)
                }
                is VideoScreenState.Error -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        videoViewModel.fetchVideo()
    }

    private fun setupPlayer() {
        player = ExoPlayer.Builder(this).build()
        findViewById<PlayerView>(R.id.player_view).player = player
    }

    private fun playVideo(url: String) {
        val mediaItem = MediaItem.fromUri(Uri.parse(url))
        player?.setMediaItem(mediaItem)
        player?.prepare()
        player?.playWhenReady = true
    }

    override fun onPause() {
        super.onPause()
        if (player?.currentMediaItem != null && !isInPictureInPictureMode) {
            enterPiPMode()
        }
    }

    override fun onBackPressed() {
        if (player?.currentMediaItem != null && !isInPictureInPictureMode) {
            enterPiPMode()
        } else {
            super.onBackPressed()
        }
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        if (player?.currentMediaItem != null) {
            enterPiPMode()
        }
    }

    private fun enterPiPMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && player?.currentMediaItem != null) {
            val params = PictureInPictureParams.Builder()
                .setAspectRatio(Rational(16, 9))
                .build()
            enterPictureInPictureMode(params)
        }
    }

    override fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode)
        if (!isInPictureInPictureMode) player?.play()
    }
}
