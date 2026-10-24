package com.example.cardetailsapplicationusingkotlin

import android.app.Activity
import android.os.Bundle
import android.widget.*

class CarDetailsActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_details)

        val car = intent.getStringExtra("car")

        val details = when (car) {
            "Maruti Suzuki" -> "Manufacturer: Maruti Suzuki\nModel: Swift\nFuel: Petrol\nSeats: 5\nPrice: ₹6.5 lakh"
            "Hyundai" -> "Manufacturer: Hyundai\nModel: i20\nFuel: Petrol\nSeats: 5\nPrice: ₹7 lakh"
            "Tata Motors" -> "Manufacturer: Tata Motors\nModel: Nexon\nFuel: Petrol/Diesel\nSeats: 5\nPrice: ₹8 lakh"
            "Honda" -> "Manufacturer: Honda\nModel: Amaze\nFuel: Petrol\nSeats: 5\nPrice: ₹8 lakh"
            "Mahindra" -> "Manufacturer: Mahindra\nModel: XUV 3XO\nFuel: Petrol/Diesel\nSeats: 5\nPrice: ₹7.5 lakh"
            else -> "Car details not found"
        }

        findViewById<TextView>(R.id.txtDetails).text = details

        findViewById<Button>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }
}