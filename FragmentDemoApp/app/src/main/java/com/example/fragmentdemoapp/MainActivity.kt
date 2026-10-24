
package com.example.fragmentdemoapp

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStudent = findViewById<Button>(R.id.btnStudent)
        val btnCourse = findViewById<Button>(R.id.btnCourse)

        // Display Student Fragment when the app starts
        if (savedInstanceState == null) {
            loadFragment(createStudentFragment())
        }

        btnStudent.setOnClickListener {
            loadFragment(createStudentFragment())
        }

        btnCourse.setOnClickListener {
            loadFragment(CourseFragment())
        }
    }

    private fun createStudentFragment(): StudentFragment {
        val fragment = StudentFragment()

        // Pass data from Activity to Fragment using Bundle
        val bundle = Bundle().apply {
            putString("studentName", "Vivek")
            putString("studentCourse", "MCA")
        }

        fragment.arguments = bundle
        return fragment
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .setReorderingAllowed(true)
            .commit()
    }
}
