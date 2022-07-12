package com.taufik.androidintemediate.localizationaccessibility.localization

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.taufik.androidintemediate.databinding.ActivityLocalizationBinding

class LocalizationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLocalizationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocalizationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        setAction()
    }

    private fun setAction() = with(binding) {
        imgSetting.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
    }
}