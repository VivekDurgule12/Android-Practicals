package com.example.foregroundservicedemo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var tvStatus: TextView
    private lateinit var btnStart: Button
    private lateinit var btnStop: Button

    // Permission launcher for Android 13+ (POST_NOTIFICATIONS)
    private val requestNotificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                startToastService()
            } else {
                Toast.makeText(
                    this,
                    "Notification permission is required to run the foreground service",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvStatus = findViewById(R.id.tvStatus)
        btnStart = findViewById(R.id.btnStart)
        btnStop = findViewById(R.id.btnStop)

        btnStart.setOnClickListener {
            checkPermissionAndStartService()
        }

        btnStop.setOnClickListener {
            stopToastService()
        }
    }

    private fun checkPermissionAndStartService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS,
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                startToastService()
            } else {
                requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        } else {
            startToastService()
        }
    }

    private fun startToastService() {
        val serviceIntent = Intent(this, ToastForegroundService::class.java)
        ContextCompat.startForegroundService(this, serviceIntent)
        tvStatus.text = getString(R.string.status_running)
    }

    private fun stopToastService() {
        val serviceIntent = Intent(this, ToastForegroundService::class.java)
        stopService(serviceIntent)
        tvStatus.text = getString(R.string.status_stopped)
    }
}
