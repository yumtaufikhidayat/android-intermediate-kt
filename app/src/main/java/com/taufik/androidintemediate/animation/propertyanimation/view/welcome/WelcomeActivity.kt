package com.taufik.androidintemediate.animation.propertyanimation.view.welcome

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.taufik.androidintemediate.animation.propertyanimation.view.login.LoginActivity
import com.taufik.androidintemediate.animation.propertyanimation.view.signup.SignUpActivity
import com.taufik.androidintemediate.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() = with(binding) {
        btnLogin.setOnClickListener {
            startActivity(Intent(this@WelcomeActivity, LoginActivity::class.java))
        }

        btnSignup.setOnClickListener {
            startActivity(Intent(this@WelcomeActivity, SignUpActivity::class.java))
        }
    }
}