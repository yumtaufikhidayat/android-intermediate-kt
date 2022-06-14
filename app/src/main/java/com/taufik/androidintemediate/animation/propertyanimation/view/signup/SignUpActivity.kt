package com.taufik.androidintemediate.animation.propertyanimation.view.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
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
import com.taufik.androidintemediate.databinding.ActivitySignupBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var signUpViewModel: SignUpViewModel

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
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
        signUpViewModel = ViewModelProvider(
            this@SignUpActivity,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[SignUpViewModel::class.java]
    }

    private fun setupAction() = with(binding) {
        btnSignup.setOnClickListener {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            when {
                name.isEmpty() -> tvNameInput.error = "Masukkan nama"
                email.isEmpty() -> tvEmailInput.error = "Masukkan email"
                password.isEmpty() -> tvPasswordInput.error = "Masukkan password"
                else -> {
                    signUpViewModel.saveUser(UserModel(name, email, password, false))
                    AlertDialog.Builder(this@SignUpActivity).apply {
                        setTitle("Yeah!")
                        setMessage("Akunnya sudah jadi nih. Yuk, login dan belajar coding.")
                        setPositiveButton("Lanjut") { _, _ ->
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

            val nameTitle = ObjectAnimator.ofFloat(tvName, View.ALPHA, 1f).setDuration(500)
            val nameInput = ObjectAnimator.ofFloat(tvNameInput, View.ALPHA, 1f).setDuration(500)

            val emailTitle = ObjectAnimator.ofFloat(tvEmail, View.ALPHA, 1f).setDuration(500)
            val emailInput = ObjectAnimator.ofFloat(tvEmailInput, View.ALPHA, 1f).setDuration(500)

            val passwordTitle = ObjectAnimator.ofFloat(tvPassword, View.ALPHA, 1f).setDuration(500)
            val passwordInput = ObjectAnimator.ofFloat(tvPasswordInput, View.ALPHA, 1f).setDuration(500)

            val signup = ObjectAnimator.ofFloat(btnSignup, View.ALPHA, 1f).setDuration(500)
            val copyright = ObjectAnimator.ofFloat(tvCopyright, View.ALPHA, 1f).setDuration(500)

            // textView dan button lainnya bergerak secara sekuensial
            AnimatorSet().apply {
                playSequentially(
                    title,
                    nameTitle, nameInput,
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