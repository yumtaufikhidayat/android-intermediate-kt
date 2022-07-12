package com.taufik.androidintemediate.localizationaccessibility.adaptivelayout

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.taufik.androidintemediate.databinding.ActivityAdaptiveLayoutBinding

class AdaptiveLayoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdaptiveLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdaptiveLayoutBinding.inflate(layoutInflater)
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