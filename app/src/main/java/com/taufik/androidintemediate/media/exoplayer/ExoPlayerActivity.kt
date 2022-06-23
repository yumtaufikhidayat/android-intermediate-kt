package com.taufik.androidintemediate.media.exoplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.util.Util
import com.taufik.androidintemediate.databinding.ActivityExoPlayerBinding

class ExoPlayerActivity : AppCompatActivity() {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityExoPlayerBinding.inflate(layoutInflater)
    }

    private var player: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()
        initializePlayer()
    }

    private fun initializePlayer() = with(binding) {
        val mediaItem = MediaItem.fromUri(URL_VIDEO_DICODING)
        val anotherMediaItem = MediaItem.fromUri(URL_AUDIO)

        player = ExoPlayer.Builder(this@ExoPlayerActivity).build().apply {
            playerVideoView.player = this
            setMediaItem(mediaItem)
            addMediaItem(anotherMediaItem)
            prepare()
        }
    }

    private fun hideSystemUI() = with(binding){
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, playerVideoView).let { windowInsetsControllerCompat ->
            windowInsetsControllerCompat.hide(WindowInsetsCompat.Type.systemBars())
            windowInsetsControllerCompat.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    private fun releasePlayer() {
        player?.release()
        player = null
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        hideSystemUI()
        if (Util.SDK_INT < 24 && player == null) {
            initializePlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 24) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }

    companion object {
        const val URL_VIDEO_DICODING = "https://github.com/dicodingacademy/assets/releases/download/release-video/VideoDicoding.mp4"
        const val URL_AUDIO = "https://github.com/dicodingacademy/assets/raw/main/android_intermediate_academy/bensound_ukulele.mp3"

    }
}