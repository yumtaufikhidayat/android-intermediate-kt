package com.taufik.androidintemediate.localizationaccessbility.formattinginformation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.taufik.androidintemediate.R
import com.taufik.androidintemediate.databinding.ActivityFormattingInformationBinding
import com.taufik.androidintemediate.localizationaccessbility.formattinginformation.Helper.withCurrencyFormat
import com.taufik.androidintemediate.localizationaccessbility.formattinginformation.Helper.withDateFormat
import com.taufik.androidintemediate.localizationaccessbility.formattinginformation.Helper.withNumberingFormat

class FormattingInformationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormattingInformationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormattingInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        setupData()
    }

    private fun setupAction() {
        binding.imgSetting.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
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

    private fun setupData() = with(binding) {
        val repository = RemoteDataSource(this@FormattingInformationActivity)
        val product = repository.getDetailProduct().apply {
            imgPreview.setImageResource(image)
            tvName.text = name
            tvStore.text = store
            tvColor.text = color
            tvSize.text = size
            tvDesc.text = desc
            tvPrice.text = price.withCurrencyFormat()
            tvDate.text = getString(R.string.date_format, date.withDateFormat())
            tvRating.text = getString(R.string.rating_format, rating.withNumberingFormat(), countRating.withNumberingFormat())
        }
    }
}