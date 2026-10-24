package com.example.userinformationapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import com.example.userinformationapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupTextChangeListeners()
        setupButtons()
    }

    private fun setupTextChangeListeners() {
        binding.etName.doOnTextChanged { _, _, _, _ ->
            binding.tilName.error = null
        }
        binding.etAge.doOnTextChanged { _, _, _, _ ->
            binding.tilAge.error = null
        }
        binding.etCity.doOnTextChanged { _, _, _, _ ->
            binding.tilCity.error = null
        }
    }

    private fun setupButtons() {
        binding.btnSubmit.setOnClickListener {
            validateAndSubmit()
        }

        binding.btnClear.setOnClickListener {
            clearFields()
        }
    }

    private fun validateAndSubmit() {
        val name = binding.etName.text?.toString()?.trim().orEmpty()
        val ageStr = binding.etAge.text?.toString()?.trim().orEmpty()
        val city = binding.etCity.text?.toString()?.trim().orEmpty()

        var isValid = true

        if (name.isEmpty()) {
            binding.tilName.error = getString(R.string.error_name_required)
            isValid = false
        } else {
            binding.tilName.error = null
        }

        if (ageStr.isEmpty()) {
            binding.tilAge.error = getString(R.string.error_age_required)
            isValid = false
        } else {
            val age = ageStr.toIntOrNull()
            if (age == null || age < 1 || age > 120) {
                binding.tilAge.error = getString(R.string.error_age_invalid)
                isValid = false
            } else {
                binding.tilAge.error = null
            }
        }

        if (city.isEmpty()) {
            binding.tilCity.error = getString(R.string.error_city_required)
            isValid = false
        } else {
            binding.tilCity.error = null
        }

        if (!isValid) {
            binding.cardResult.visibility = View.GONE
            Toast.makeText(this, "Please fill in all required fields correctly.", Toast.LENGTH_SHORT).show()
            return
        }

        val personalizedMsg = getString(R.string.personalized_message, name, ageStr, city)
        binding.tvResult.text = personalizedMsg
        binding.cardResult.visibility = View.VISIBLE
    }

    private fun clearFields() {
        binding.etName.text?.clear()
        binding.etAge.text?.clear()
        binding.etCity.text?.clear()

        binding.tilName.error = null
        binding.tilAge.error = null
        binding.tilCity.error = null

        binding.cardResult.visibility = View.GONE
        binding.tvResult.text = ""

        binding.main.clearFocus()
    }
}