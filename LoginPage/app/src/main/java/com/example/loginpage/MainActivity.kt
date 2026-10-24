package com.example.loginpage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val name = findViewById<EditText>(R.id.Username)
        val pass = findViewById<EditText>(R.id.password)

        val login = findViewById<Button>(R.id.button)
        
        
        login.setOnClickListener {

            val username = name.text.toString()
            val password = pass.text.toString()

            val sharedPreferences = getSharedPreferences(
                "loginData",
                MODE_PRIVATE
            )

            val editor = sharedPreferences.edit()


            editor.putString(
                "Username",
                username
            ).apply()

            editor.putString(
                "Password",
                password
            ).apply()
            
            
            

            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)

        }




    }

}