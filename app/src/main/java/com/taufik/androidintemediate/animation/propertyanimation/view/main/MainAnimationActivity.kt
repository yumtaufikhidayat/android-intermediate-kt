package com.taufik.androidintemediate.animation.propertyanimation.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.taufik.androidintemediate.databinding.ActivityMainAnimationBinding

class MainAnimationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainAnimationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainAnimationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}