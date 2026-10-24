package com.example.activitylifecycledemo

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.widget.*

class MainActivity : Activity() {

    private lateinit var txtMessage: TextView

    private fun showEvent(message: String) {
        Log.d("LifeCycle", message)
        if (::txtMessage.isInitialized) {
            txtMessage.text = message
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtMessage = findViewById(R.id.txtMessage)

        findViewById<Button>(R.id.btnNext).setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }

        showEvent("onCreate() called")
    }

    override fun onStart() {
        super.onStart()
        showEvent("onStart() called")
    }

    override fun onResume() {
        super.onResume()
        showEvent("onResume() called")
    }

    override fun onPause() {
        showEvent("onPause() called")
        super.onPause()
    }

    override fun onStop() {
        showEvent("onStop() called")
        super.onStop()
    }

    override fun onRestart() {
        super.onRestart()
        showEvent("onRestart() called")
    }

    override fun onDestroy() {
        showEvent("onDestroy() called")
        super.onDestroy()
    }
}