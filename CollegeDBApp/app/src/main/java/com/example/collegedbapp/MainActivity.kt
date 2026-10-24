
package com.example.collegedbapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var edtStudentID: EditText
    private lateinit var edtName: EditText
    private lateinit var edtCourse: EditText
    private lateinit var edtMobile: EditText
    private lateinit var listStudents: ListView

    private lateinit var adapter: ArrayAdapter<String>
    private val studentRecords = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)

        edtStudentID = findViewById(R.id.edtStudentID)
        edtName = findViewById(R.id.edtName)
        edtCourse = findViewById(R.id.edtCourse)
        edtMobile = findViewById(R.id.edtMobile)
        listStudents = findViewById(R.id.listStudents)

        val btnInsert = findViewById<Button>(R.id.btnInsert)
        val btnView = findViewById<Button>(R.id.btnView)
        val btnUpdate = findViewById<Button>(R.id.btnUpdate)
        val btnDelete = findViewById<Button>(R.id.btnDelete)

        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            mutableListOf<String>()
        )
        listStudents.adapter = adapter

        btnInsert.setOnClickListener {
            insertStudent()
        }

        btnView.setOnClickListener {
            loadStudents()
        }

        btnUpdate.setOnClickListener {
            updateStudent()
        }

        btnDelete.setOnClickListener {
            deleteStudent()
        }

        listStudents.setOnItemClickListener { _, _, position, _ ->
            val record = studentRecords[position].split("|")

            edtStudentID.setText(record[0])
            edtName.setText(record[1])
            edtCourse.setText(record[2])
            edtMobile.setText(record[3])
        }

        loadStudents()
    }

    private fun validateInput(): Boolean {
        val id = edtStudentID.text.toString().trim()
        val name = edtName.text.toString().trim()
        val course = edtCourse.text.toString().trim()
        val mobile = edtMobile.text.toString().trim()

        if (id.isEmpty() || name.isEmpty() ||
            course.isEmpty() || mobile.isEmpty()
        ) {
            showMessage("Please fill in all fields")
            return false
        }

        if (id.toIntOrNull() == null || id.toInt() <= 0) {
            edtStudentID.error = "Enter a valid positive ID"
            return false
        }

        if (name.any { it.isDigit() }) {
            edtName.error = "Name must not contain numbers"
            return false
        }

        if (!mobile.matches(Regex("[0-9]{10}"))) {
            edtMobile.error = "Enter a valid 10-digit mobile number"
            return false
        }

        return true
    }

    private fun insertStudent() {
        if (!validateInput()) return

        val success = dbHelper.insertStudent(
            edtStudentID.text.toString().trim().toInt(),
            edtName.text.toString().trim(),
            edtCourse.text.toString().trim(),
            edtMobile.text.toString().trim()
        )

        if (success) {
            showMessage("Student inserted successfully")
            clearForm()
            loadStudents()
        } else {
            showMessage("Student ID already exists")
        }
    }

    private fun loadStudents() {
        studentRecords.clear()
        studentRecords.addAll(dbHelper.getAllStudents())

        val displayRecords = studentRecords.map { record ->
            val parts = record.split("|")
            "ID: ${parts[0]}\n" +
                    "Name: ${parts[1]}\n" +
                    "Course: ${parts[2]}\n" +
                    "Mobile: ${parts[3]}"
        }

        adapter.clear()
        adapter.addAll(displayRecords)
        adapter.notifyDataSetChanged()

        if (studentRecords.isEmpty()) {
            showMessage("No student records found")
        }
    }

    private fun updateStudent() {
        if (!validateInput()) return

        val success = dbHelper.updateStudent(
            edtStudentID.text.toString().trim().toInt(),
            edtName.text.toString().trim(),
            edtCourse.text.toString().trim(),
            edtMobile.text.toString().trim()
        )

        if (success) {
            showMessage("Student updated successfully")
            clearForm()
            loadStudents()
        } else {
            showMessage("Student ID not found")
        }
    }

    private fun deleteStudent() {
        val idText = edtStudentID.text.toString().trim()
        val id = idText.toIntOrNull()

        if (id == null || id <= 0) {
            edtStudentID.error = "Enter or select a valid Student ID"
            return
        }

        val success = dbHelper.deleteStudent(id)

        if (success) {
            showMessage("Student deleted successfully")
            clearForm()
            loadStudents()
        } else {
            showMessage("Student ID not found")
        }
    }

    private fun clearForm() {
        edtStudentID.text.clear()
        edtName.text.clear()
        edtCourse.text.clear()
        edtMobile.text.clear()
    }

    private fun showMessage(message: String) {
        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}
