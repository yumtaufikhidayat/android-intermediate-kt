package com.taufik.androidintemediate.animation.propertyanimation.view.welcome

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
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
        playAnimation()
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

    private fun playAnimation() = with(binding) {
        /*
            objek gambar
            bergerak secara horizontal
            dengan durasi 6 detik
            bergerak sejauh 60f (-30f s.d. 30f)
            kembali ke titik semula (repeatMode = reverse)
            animasi terus berjalan (repeatCount = infinity)
        */
        ObjectAnimator.ofFloat(imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        // Menambahkan animasi untuk masing-masing view
        val login = ObjectAnimator.ofFloat(btnLogin, View.ALPHA, 1f).setDuration(500)
        val signup = ObjectAnimator.ofFloat(btnSignup, View.ALPHA, 1f).setDuration(500)
        val title = ObjectAnimator.ofFloat(tvTitle, View.ALPHA, 1f).setDuration(500)
        val desc = ObjectAnimator.ofFloat(tvDesc, View.ALPHA, 1f).setDuration(500)

        // dua button bergerak secara bersamaan
        val together = AnimatorSet().apply {
            playTogether(login, signup)
        }

        // tiga TextView lainnya bergerak secara sekuensial
        AnimatorSet().apply {
            playSequentially(title, desc, together)
            start()
        }
    }
}