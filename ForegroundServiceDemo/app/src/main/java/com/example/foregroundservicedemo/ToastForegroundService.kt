package com.example.foregroundservicedemo

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat

class ToastForegroundService : Service() {

    companion object {
        private const val CHANNEL_ID = "ToastServiceChannel"
        private const val NOTIFICATION_ID = 1
        private const val TOAST_INTERVAL = 10000L // 10 seconds
    }

    private val handler = Handler(Looper.getMainLooper())
    private var isServiceRunning = false

    // Runnable to display Toast every 10 seconds
    private val toastRunnable = object : Runnable {
        override fun run() {
            if (isServiceRunning) {
                Toast.makeText(
                    applicationContext,
                    "Service Active: Toast message every 10 seconds",
                    Toast.LENGTH_SHORT,
                ).show()
                // Schedule the next Toast after 10 seconds (10,000 ms)
                handler.postDelayed(this, TOAST_INTERVAL)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!isServiceRunning) {
            isServiceRunning = true

            // Build persistent notification
            val notification = createNotification()

            // Start service in foreground
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ServiceCompat.startForeground(
                    this,
                    NOTIFICATION_ID,
                    notification,
                    ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC,
                )
            } else {
                startForeground(NOTIFICATION_ID, notification)
            }

            // Start periodic Toast task
            handler.post(toastRunnable)
            Toast.makeText(this, "Foreground Service Started", Toast.LENGTH_SHORT).show()
        }

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        isServiceRunning = false
        handler.removeCallbacks(toastRunnable)
        Toast.makeText(this, "Foreground Service Stopped", Toast.LENGTH_SHORT).show()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Foreground Toast Service Channel",
                NotificationManager.IMPORTANCE_LOW,
            ).apply {
                description = "Channel for Foreground Service Toast Demo"
            }
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Toast Service Running")
            .setContentText("Displaying Toast messages every 10 seconds.")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }
}
