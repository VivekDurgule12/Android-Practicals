package com.example.loginpage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)


        val showUsername = findViewById<TextView>(R.id.tvShowUsername)
        val showPassword = findViewById<TextView>(R.id.tvShowPassword)


        val sharedPreferences = getSharedPreferences(
            "loginData",
            MODE_PRIVATE
        )



        val savedUsername =
            sharedPreferences.getString(
                "Username",
                "")

        val savedPassword =
            sharedPreferences.getString(
                "Password",
                "")


        showUsername.text = "Welcome, $savedUsername!"
        showPassword.text = "Your password is: $savedPassword"

        AlertDialog.Builder(this)
            .setTitle("Login")
            .setMessage("Login Successfully")
            .setPositiveButton("OK", null)
            .show()




        val btn = findViewById<Button>(R.id.button2)
        btn.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }





    }





    }
