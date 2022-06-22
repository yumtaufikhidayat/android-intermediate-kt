package com.taufik.androidintemediate.media.soundpool

import android.media.SoundPool
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.taufik.androidintemediate.R
import com.taufik.androidintemediate.databinding.ActivitySoundPoolBinding

class SoundPoolActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySoundPoolBinding
    private lateinit var sp: SoundPool
    private var soundId = 0
    private var spLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySoundPoolBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSoundPool()
        setAction()
    }

    private fun setSoundPool() = with(binding) {
        sp = SoundPool.Builder()
            .setMaxStreams(10)
            .build()

        sp.setOnLoadCompleteListener {_, _, status ->
            if (status == 0) {
                spLoaded = true
            } else {
                Toast.makeText(this@SoundPoolActivity, "Gagal load", Toast.LENGTH_SHORT).show()
            }
        }

        soundId = sp.load(this@SoundPoolActivity, R.raw.clinking_glasses, 1)
    }

    private fun setAction() = with(binding) {
        btnPlaySound.setOnClickListener {
            if (spLoaded) {
                sp.play(soundId, 1f, 1f, 0, 0, 1f)
            }
        }
    }
}