package com.taufik.androidintemediate.service.service

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.taufik.androidintemediate.databinding.ActivityServiceBinding

class ServiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityServiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAction()
    }

    private fun setAction() = with(binding) {
        btnStartService.setOnClickListener {
            startService(Intent(this@ServiceActivity, MyService::class.java))
        }

        btnStartBoundService.setOnClickListener {

        }

        btnStopBoundService.setOnClickListener {

        }
    }
}