package com.taufik.androidintemediate.advancedui.customviewfromscratch

import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.taufik.androidintemediate.databinding.ActivitySeatsBinding

class SeatsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySeatsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeatsBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(binding.root)

        setActionClick()
    }

    private fun setActionClick() = with(binding) {
        btnFinish.setOnClickListener {
            seatsView.seat?.let {
                Toast.makeText(this@SeatsActivity, "Kursi Anda nomor ${it.name}", Toast.LENGTH_SHORT).show()
            } ?: run {
                Toast.makeText(this@SeatsActivity, "Silakan pilih kursi terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
        }
    }
}