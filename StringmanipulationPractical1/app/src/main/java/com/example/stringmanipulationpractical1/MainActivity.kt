package com.example.stringmanipulationpractical1

import android.os.Bundle
import android.graphics.Typeface
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.stringmanipulationpractical1.R

class MainActivity : AppCompatActivity() {

    private lateinit var txtMessage: TextView

    private var fontSize = 28f
    private var fontIndex = 0

    private val fonts = arrayOf(
        "sans-serif",
        "serif",
        "monospace"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtMessage = findViewById(R.id.txtMessage)

        findViewById<Button>(R.id.btnBold).setOnClickListener {
            txtMessage.setTypeface(txtMessage.typeface, Typeface.BOLD)
        }

        findViewById<Button>(R.id.btnItalic).setOnClickListener {
            txtMessage.setTypeface(txtMessage.typeface, Typeface.ITALIC)
        }

        findViewById<Button>(R.id.btnNormal).setOnClickListener {
            txtMessage.setTypeface(txtMessage.typeface, Typeface.NORMAL)
        }

        findViewById<Button>(R.id.btnFont).setOnClickListener {
            fontIndex = (fontIndex + 1) % fonts.size
            txtMessage.typeface =
                Typeface.create(fonts[fontIndex], Typeface.NORMAL)
        }

        findViewById<Button>(R.id.btnSize).setOnClickListener {
            fontSize += 4f

            if (fontSize > 44f) {
                fontSize = 20f
            }

            txtMessage.textSize = fontSize
        }

        findViewById<Button>(R.id.btnUpper).setOnClickListener {
            txtMessage.text =
                txtMessage.text.toString().uppercase()
        }

        findViewById<Button>(R.id.btnLower).setOnClickListener {
            txtMessage.text =
                txtMessage.text.toString().lowercase()
        }

        findViewById<Button>(R.id.btnConcat).setOnClickListener {
            txtMessage.text =
                txtMessage.text.toString() + " Kotlin"
        }
    }
}
