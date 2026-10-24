package com.example.activitylifecycledemo

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class SecondActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        Log.d("LifeCycle", "SecondActivity onCreate() called")

        findViewById<Button>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }
}