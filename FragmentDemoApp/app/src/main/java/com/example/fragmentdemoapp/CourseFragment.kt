
package com.example.fragmentdemoapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class CourseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("CourseFragment", "onCreate called")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_course,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.txtCourseName).text =
            "Course: Master of Computer Applications"

        view.findViewById<TextView>(R.id.txtDuration).text =
            "Duration: 2 Years"

        Log.d("CourseFragment", "onViewCreated called")
    }

    override fun onStart() {
        super.onStart()
        Log.d("CourseFragment", "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d("CourseFragment", "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d("CourseFragment", "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d("CourseFragment", "onStop called")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("CourseFragment", "onDestroyView called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("CourseFragment", "onDestroy called")
    }
}
