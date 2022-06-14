package com.taufik.androidintemediate.animation.propertyanimation.view.signup

import android.content.Context
import android.os.Build
import android.os.Bundle
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

    private fun setupViewModel() = with(binding) {
        signUpViewModel = ViewModelProvider(
            this@SignUpActivity,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[SignUpViewModel::class.java]
    }

    private fun setupAction() = with(binding) {
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