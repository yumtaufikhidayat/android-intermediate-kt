package com.taufik.androidintemediate.animation.propertyanimation.view.main

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
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
}