package com.taufik.androidintemediate.animation.motionlayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.taufik.androidintemediate.databinding.ActivityMotionMainBinding

class MotionMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMotionMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMotionMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
    }
}