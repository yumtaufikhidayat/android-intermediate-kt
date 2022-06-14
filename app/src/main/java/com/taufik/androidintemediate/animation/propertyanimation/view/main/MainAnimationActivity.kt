package com.taufik.androidintemediate.animation.propertyanimation.view.main

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.taufik.androidintemediate.R
import com.taufik.androidintemediate.animation.propertyanimation.model.UserPreference
import com.taufik.androidintemediate.animation.propertyanimation.view.ViewModelFactory
import com.taufik.androidintemediate.animation.propertyanimation.view.welcome.WelcomeActivity
import com.taufik.androidintemediate.databinding.ActivityMainAnimationBinding

class MainAnimationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainAnimationBinding
    private lateinit var mainViewModel: MainViewModel

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainAnimationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
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

    private fun setupViewModel() = with(binding){
        mainViewModel = ViewModelProvider(
            this@MainAnimationActivity,
        ViewModelFactory(UserPreference.getInstance(dataStore))
        )[MainViewModel::class.java]

        mainViewModel.getUser().observe(this@MainAnimationActivity) { user ->
            if (user.isLogin) {
                tvName.text = getString(R.string.greeting, user.name)
            } else {
                startActivity(Intent(this@MainAnimationActivity, WelcomeActivity::class.java))
                finish()
            }
        }
    }

    private fun setupAction() = with(binding) {
        btnLogout.setOnClickListener {
            mainViewModel.logout()
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

            val name = ObjectAnimator.ofFloat(tvName, View.ALPHA, 1f).setDuration(500)
            val message = ObjectAnimator.ofFloat(tvMessage, View.ALPHA, 1f).setDuration(500)
            val logout = ObjectAnimator.ofFloat(btnLogout, View.ALPHA, 1f).setDuration(500)
            val copyright = ObjectAnimator.ofFloat(tvCopyright, View.ALPHA, 1f).setDuration(500)

            // textView dan button lainnya bergerak secara sekuensial
            AnimatorSet().apply {
                playSequentially(
                    name,
                    message,
                    logout,
                    copyright
                )
                start()
            }
        }
    }
}