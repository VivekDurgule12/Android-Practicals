
package com.example.sharedpreferencesapp

import android.os.Bundle
import android.graphics.Color
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var edtUsername: EditText
    private lateinit var edtEmail: EditText
    private lateinit var spinnerTheme: Spinner
    private lateinit var mainLayout: LinearLayout
    private lateinit var txtTitle: TextView
    private lateinit var txtSavedDetails: TextView

    private val preferences by lazy {
        getSharedPreferences("UserPrefs", MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtUsername = findViewById(R.id.edtUsername)
        edtEmail = findViewById(R.id.edtEmail)
        spinnerTheme = findViewById(R.id.spinnerTheme)
        mainLayout = findViewById(R.id.mainLayout)
        txtTitle = findViewById(R.id.txtTitle)
        txtSavedDetails = findViewById(R.id.txtSavedDetails)

        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnUpdate = findViewById<Button>(R.id.btnUpdate)
        val btnClear = findViewById<Button>(R.id.btnClear)

        // Restore preferences when the app opens
        loadPreferences()

        btnSave.setOnClickListener {
            savePreferences()
        }

        btnUpdate.setOnClickListener {
            savePreferences()
        }

        btnClear.setOnClickListener {
            clearPreferences()
        }
    }

    private fun savePreferences() {
        val username = edtUsername.text.toString().trim()
        val email = edtEmail.text.toString().trim()
        val theme = spinnerTheme.selectedItem.toString()

        if (username.isEmpty() || email.isEmpty()) {
            Toast.makeText(
                this,
                "Please enter username and email",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS
                .matcher(email).matches()) {
            edtEmail.error = "Enter a valid email"
            return
        }

        preferences.edit().apply {
            putString("username", username)
            putString("email", email)
            putString("theme", theme)
            apply()
        }

        applyTheme(theme)
        displaySavedDetails()

        Toast.makeText(
            this,
            "Preferences saved successfully",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun loadPreferences() {
        val username = preferences.getString("username", "") ?: ""
        val email = preferences.getString("email", "") ?: ""
        val theme = preferences.getString("theme", "Light") ?: "Light"

        edtUsername.setText(username)
        edtEmail.setText(email)

        val position = if (theme == "Dark") 1 else 0
        spinnerTheme.setSelection(position)

        applyTheme(theme)
        displaySavedDetails()
    }

    private fun displaySavedDetails() {
        val username = preferences.getString("username", null)
        val email = preferences.getString("email", null)
        val theme = preferences.getString("theme", null)

        txtSavedDetails.text =
            if (username != null && email != null && theme != null) {
                "Saved Preferences\n\n" +
                        "Username: $username\n" +
                        "Email: $email\n" +
                        "Theme: $theme"
            } else {
                "No preferences saved."
            }
    }

    private fun applyTheme(theme: String) {
        if (theme == "Dark") {
            mainLayout.setBackgroundColor(Color.DKGRAY)
            txtTitle.setTextColor(Color.WHITE)
            txtSavedDetails.setTextColor(Color.WHITE)
        } else {
            mainLayout.setBackgroundColor(Color.WHITE)
            txtTitle.setTextColor(Color.BLACK)
            txtSavedDetails.setTextColor(Color.BLACK)
        }
    }

    private fun clearPreferences() {
        preferences.edit().apply {
            remove("username")
            remove("email")
            remove("theme")
            apply()
        }

        edtUsername.text.clear()
        edtEmail.text.clear()
        spinnerTheme.setSelection(0)

        applyTheme("Light")
        displaySavedDetails()

        Toast.makeText(
            this,
            "Preferences cleared",
            Toast.LENGTH_SHORT
        ).show()
    }
}
