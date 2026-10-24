
package com.example.fragmentdemoapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class StudentFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("StudentFragment", "onCreate called")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_student,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val name = arguments?.getString("studentName") ?: "Student"
        val course = arguments?.getString("studentCourse") ?: "MCA"

        view.findViewById<TextView>(R.id.txtStudentName).text =
            "Name: $name"

        view.findViewById<TextView>(R.id.txtStudentCourse).text =
            "Course: $course"

        Log.d("StudentFragment", "onViewCreated called")
    }

    override fun onStart() {
        super.onStart()
        Log.d("StudentFragment", "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d("StudentFragment", "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d("StudentFragment", "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d("StudentFragment", "onStop called")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("StudentFragment", "onDestroyView called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("StudentFragment", "onDestroy called")
    }
}
