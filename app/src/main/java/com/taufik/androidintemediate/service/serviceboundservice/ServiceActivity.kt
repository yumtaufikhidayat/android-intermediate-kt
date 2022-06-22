package com.taufik.androidintemediate.service.serviceboundservice

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.taufik.androidintemediate.databinding.ActivityServiceBinding
import com.taufik.androidintemediate.service.serviceboundservice.MyBoundService.MyBinder

class ServiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityServiceBinding
    private lateinit var mBoundService: MyBoundService

    private var mServiceBound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAction()
    }

    private val mServiceConnection = object: ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val myBinder = service as MyBinder
            mBoundService = myBinder.getService
            mServiceBound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            mServiceBound = false
        }
    }

    private fun setAction() = with(binding) {
        btnStartService.setOnClickListener {
            startService(Intent(this@ServiceActivity, MyService::class.java))
        }

        btnStartBoundService.setOnClickListener {
            val mBoundServiceIntent = Intent(this@ServiceActivity, MyBoundService::class.java)
            bindService(mBoundServiceIntent, mServiceConnection, BIND_AUTO_CREATE)
        }

        btnStopBoundService.setOnClickListener {
            unbindService(mServiceConnection)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mServiceBound) {
            unbindService(mServiceConnection)
        }
    }
}