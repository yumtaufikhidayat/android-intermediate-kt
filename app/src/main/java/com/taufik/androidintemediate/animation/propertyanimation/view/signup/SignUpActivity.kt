package com.taufik.androidintemediate.animation.propertyanimation.view.signup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.taufik.androidintemediate.databinding.ActivitySignupBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}