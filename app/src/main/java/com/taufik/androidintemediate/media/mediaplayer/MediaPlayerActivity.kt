package com.taufik.androidintemediate.media.mediaplayer

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.taufik.androidintemediate.R
import com.taufik.androidintemediate.databinding.ActivityMediaPlayerBinding
import java.io.IOException

class MediaPlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMediaPlayerBinding

    private var mMediaPlayer: MediaPlayer? = null
    private var isReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        setAction()
    }

    private fun init() {
        mMediaPlayer = MediaPlayer()
        val attribute = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        mMediaPlayer?.apply {

            setAudioAttributes(attribute)

            val afd = applicationContext.resources.openRawResourceFd(R.raw.guitar_background)
            try {
                mMediaPlayer?.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            setOnPreparedListener {
                isReady = true
                mMediaPlayer?.start()
            }

            setOnErrorListener { _, _, _ -> false }
        }
    }

    private fun setAction() = with(binding) {
        btnPlaySound.setOnClickListener {
            if (!isReady) {
                mMediaPlayer?.prepareAsync()
            } else {
                if (mMediaPlayer?.isPlaying as Boolean) {
                    mMediaPlayer?.pause()
                } else {
                    mMediaPlayer?.start()
                }
            }
        }

        btnStopSound.setOnClickListener {
            if (mMediaPlayer?.isPlaying as Boolean || isReady) {
                mMediaPlayer?.stop()
                isReady = false
            }
        }
    }
}