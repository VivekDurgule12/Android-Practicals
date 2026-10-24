package com.example.cardetailsapplicationusingkotlin

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.widget.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)

        btnSubmit.setOnClickListener {
            val id = radioGroup.checkedRadioButtonId

            if (id == -1) {
                Toast.makeText(this, "Select a car", Toast.LENGTH_SHORT).show()
            } else {
                val car = findViewById<RadioButton>(id).text.toString()

                val intent = Intent(this, CarDetailsActivity::class.java)
                intent.putExtra("car", car)
                startActivity(intent)
            }
        }
    }
}