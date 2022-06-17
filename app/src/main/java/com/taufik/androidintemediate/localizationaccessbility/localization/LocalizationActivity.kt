package com.taufik.androidintemediate.localizationaccessbility.localization

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.taufik.androidintemediate.databinding.ActivityLocalizationBinding

class LocalizationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLocalizationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocalizationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}