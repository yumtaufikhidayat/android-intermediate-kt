package com.taufik.androidintemediate.animation.propertyanimation.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.taufik.androidintemediate.animation.propertyanimation.model.UserModel
import com.taufik.androidintemediate.animation.propertyanimation.model.UserPreference
import com.taufik.androidintemediate.animation.propertyanimation.view.ViewModelFactory
import com.taufik.androidintemediate.animation.propertyanimation.view.main.MainAnimationActivity
import com.taufik.androidintemediate.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var userModel: UserModel

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
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

    private fun setupViewModel() {
        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[LoginViewModel::class.java]

        loginViewModel.getUser().observe(this) { user ->
            this.userModel = user
        }
    }

    private fun setupAction() = with(binding) {
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            when {
                email.isEmpty() -> tvEmailInput.error = "Masukkan email"
                password.isEmpty() -> tvPasswordInput.error = "Masukkan password"
                email != userModel.email -> etEmail.error = "Email tidak sesuai"
                password != userModel.password -> etPassword.error = "Password tidak sesuai"
                else -> {
                    loginViewModel.login()
                    AlertDialog.Builder(this@LoginActivity).apply {
                        setTitle("Yeah")
                        setMessage("Anda berhasil login. Sudah tidak sabar untuk belajar ya?")
                        setPositiveButton("Lanjut") { _, _ ->
                            val intent = Intent(context, MainAnimationActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                        }
                        create()
                        show()
                    }
                }
            }
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
        ObjectAnimator.ofFloat(imageView, View.TRANSLATION_X, - 30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE

            // Menambahkan animasi untuk masing-masing view
            val title = ObjectAnimator.ofFloat(tvTitle, View.ALPHA, 1f).setDuration(500)
            val message = ObjectAnimator.ofFloat(tvMessage, View.ALPHA, 1f).setDuration(500)

            val emailTitle = ObjectAnimator.ofFloat(tvEmail, View.ALPHA, 1f).setDuration(500)
            val emailInput = ObjectAnimator.ofFloat(tvEmailInput, View.ALPHA, 1f).setDuration(500)

            val passwordTitle = ObjectAnimator.ofFloat(tvPassword, View.ALPHA, 1f).setDuration(500)
            val passwordInput = ObjectAnimator.ofFloat(tvPasswordInput, View.ALPHA, 1f).setDuration(500)

            val signup = ObjectAnimator.ofFloat(btnLogin, View.ALPHA, 1f).setDuration(500)
            val copyright = ObjectAnimator.ofFloat(tvCopyright, View.ALPHA, 1f).setDuration(500)

            AnimatorSet().apply {
                playSequentially(
                    title, message,
                    emailTitle, emailInput,
                    passwordTitle, passwordInput,
                    signup,
                    copyright
                )
                start()
            }
        }
    }
}