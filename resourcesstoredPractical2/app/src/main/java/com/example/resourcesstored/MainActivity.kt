package com.example.resourcesstored

import android.os.Bundle
import android.view.View
import android.util.TypedValue
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val text = findViewById<TextView>(R.id.txtDisplay)
        val spinner = findViewById<Spinner>(R.id.spinner)

        spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        0 -> text.text = getString(R.string.welcome)

                        1 -> text.text = getString(R.string.message)

                        2 -> text.setTextColor(
                            ContextCompat.getColor(
                                this@MainActivity, R.color.red
                            )
                        )

                        3 -> text.setTextColor(
                            ContextCompat.getColor(
                                this@MainActivity, R.color.blue
                            )
                        )

                        4 -> text.setTextColor(
                            ContextCompat.getColor(
                                this@MainActivity, R.color.green
                            )
                        )

                        5 -> text.setTextSize(
                            TypedValue.COMPLEX_UNIT_PX,
                            resources.getDimension(R.dimen.small)
                        )

                        6 -> text.setTextSize(
                            TypedValue.COMPLEX_UNIT_PX,
                            resources.getDimension(R.dimen.medium)
                        )

                        7 -> text.setTextSize(
                            TypedValue.COMPLEX_UNIT_PX,
                            resources.getDimension(R.dimen.large)
                        )
                    }
                }

                override fun onNothingSelected(
                    parent: AdapterView<*>?
                ) {}
            }
    }
}
