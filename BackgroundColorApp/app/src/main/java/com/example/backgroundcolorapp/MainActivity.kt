package com.example.backgroundcolorapp

import android.os.Bundle
import android.graphics.Color
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mainLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainLayout = findViewById(R.id.mainLayout)
    }

    // Create the Options Menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // Handle menu item selections
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.red -> {
                mainLayout.setBackgroundColor(Color.RED)
                Toast.makeText(this, "Red selected", Toast.LENGTH_SHORT).show()
            }

            R.id.green -> {
                mainLayout.setBackgroundColor(Color.GREEN)
                Toast.makeText(this, "Green selected", Toast.LENGTH_SHORT).show()
            }

            R.id.blue -> {
                mainLayout.setBackgroundColor(Color.BLUE)
                Toast.makeText(this, "Blue selected", Toast.LENGTH_SHORT).show()
            }

            R.id.yellow -> {
                mainLayout.setBackgroundColor(Color.YELLOW)
                Toast.makeText(this, "Yellow selected", Toast.LENGTH_SHORT).show()
            }

            R.id.reset -> {
                mainLayout.setBackgroundColor(Color.WHITE)
                Toast.makeText(this, "Color reset", Toast.LENGTH_SHORT).show()
            }

            else -> return super.onOptionsItemSelected(item)
        }

        return true
    }
}