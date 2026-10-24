package com.example.implicitintentdemo

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etUrl = findViewById<EditText>(R.id.etUrl)
        val btnOpenWebsite = findViewById<Button>(R.id.btnOpenWebsite)

        val etPhone = findViewById<EditText>(R.id.etPhone)
        val btnDialPhone = findViewById<Button>(R.id.btnDialPhone)

        val etEmailAddress = findViewById<EditText>(R.id.etEmailAddress)
        val etEmailSubject = findViewById<EditText>(R.id.etEmailSubject)
        val etEmailBody = findViewById<EditText>(R.id.etEmailBody)
        val btnSendEmail = findViewById<Button>(R.id.btnSendEmail)

        // 1. Action: Open Website
        btnOpenWebsite.setOnClickListener {
            val urlText = etUrl.text.toString().trim()
            if (urlText.isNotEmpty()) {
                openWebsite(urlText)
            } else {
                Toast.makeText(this, "Please enter a valid URL", Toast.LENGTH_SHORT).show()
            }
        }

        // 2. Action: Dial Phone Number
        btnDialPhone.setOnClickListener {
            val phoneNumber = etPhone.text.toString().trim()
            if (phoneNumber.isNotEmpty()) {
                dialPhoneNumber(phoneNumber)
            } else {
                Toast.makeText(this, "Please enter a phone number", Toast.LENGTH_SHORT).show()
            }
        }

        // 3. Action: Compose Email
        btnSendEmail.setOnClickListener {
            val email = etEmailAddress.text.toString().trim()
            val subject = etEmailSubject.text.toString().trim()
            val body = etEmailBody.text.toString().trim()

            if (email.isNotEmpty()) {
                composeEmail(arrayOf(email), subject, body)
            } else {
                Toast.makeText(this, "Please enter an email address", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openWebsite(url: String) {
        val formattedUrl = if (!url.startsWith("http://") && !url.startsWith("https://")) {
            "https://$url"
        } else {
            url
        }
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(formattedUrl))
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "No web browser application found!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun dialPhoneNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "No phone app found to dial!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun composeEmail(addresses: Array<String>, subject: String, body: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // Ensure only email apps handle this
            putExtra(Intent.EXTRA_EMAIL, addresses)
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
        }
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "No email client app found!", Toast.LENGTH_SHORT).show()
        }
    }
}