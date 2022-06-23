package com.taufik.androidintemediate.media.mediaplayerservice

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.*
import android.util.Log
import com.taufik.androidintemediate.R
import java.io.IOException
import java.lang.ref.WeakReference

class MediaService: Service(), MediaPlayerCallback {

    private var isReady: Boolean = false
    private var mMediaPlayer: MediaPlayer? = null

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: ")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val action = intent.action
        if (action != null) {
            when (action) {
                ACTION_CREATE -> if (mMediaPlayer == null) {
                    init()
                }

                ACTION_DESTROY -> if (mMediaPlayer?.isPlaying as Boolean) {
                    stopSelf()
                }

                else -> init()
            }
        }
        Log.d(TAG, "onStartCommand: ")
        return flags
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.d(TAG, "onBind: ")
        return mMessenger.binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "onUnbind: ")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
        mMediaPlayer?.release()
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

    override fun onPlay() {
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

    override fun onStop() {
        if (mMediaPlayer?.isPlaying as Boolean || isReady) {
            mMediaPlayer?.stop()
            isReady = false
        }
    }

    private val mMessenger = Messenger(IncomingHandler(this))

    internal class IncomingHandler(playerCallback: MediaPlayerCallback) : Handler(Looper.getMainLooper()) {

        private val mediaPlayerCallbackWeakReference: WeakReference<MediaPlayerCallback> = WeakReference(playerCallback)

        override fun handleMessage(msg: Message) {
            when (msg.what) {
                PLAY -> mediaPlayerCallbackWeakReference.get()?.onPlay()
                STOP -> mediaPlayerCallbackWeakReference.get()?.onStop()
                else -> super.handleMessage(msg)
            }
        }
    }

    companion object {
        const val ACTION_CREATE = "com.taufik.androidintemediate.media.mediaplayerservice.ACTION_CREATE"
        const val ACTION_DESTROY = "com.taufik.androidintemediate.media.mediaplayerservice.ACTION_DESTROY"
        const val TAG = "MediaService"
        const val PLAY = 0
        const val STOP = 1
    }
}