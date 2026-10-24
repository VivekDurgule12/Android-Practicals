
package com.example.collegedbapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "CollegeDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE Student (
                StudentID INTEGER PRIMARY KEY,
                Name TEXT NOT NULL,
                Course TEXT NOT NULL,
                MobileNumber TEXT NOT NULL
            )
        """.trimIndent()

        db.execSQL(createTable)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        // Add migration logic here if the schema changes.
    }

    fun insertStudent(
        id: Int,
        name: String,
        course: String,
        mobile: String
    ): Boolean {
        val values = ContentValues().apply {
            put("StudentID", id)
            put("Name", name)
            put("Course", course)
            put("MobileNumber", mobile)
        }

        return try {
            writableDatabase.insertOrThrow(
                "Student",
                null,
                values
            ) != -1L
        } catch (e: SQLiteConstraintException) {
            false
        }
    }

    fun getAllStudents(): List<String> {
        val students = mutableListOf<String>()

        val cursor = readableDatabase.rawQuery(
            "SELECT StudentID, Name, Course, MobileNumber " +
                    "FROM Student ORDER BY StudentID",
            null
        )

        cursor.use {
            while (it.moveToNext()) {
                val id = it.getInt(0)
                val name = it.getString(1)
                val course = it.getString(2)
                val mobile = it.getString(3)

                students.add(
                    "$id|$name|$course|$mobile"
                )
            }
        }

        return students
    }

    fun updateStudent(
        id: Int,
        name: String,
        course: String,
        mobile: String
    ): Boolean {
        val values = ContentValues().apply {
            put("Name", name)
            put("Course", course)
            put("MobileNumber", mobile)
        }

        return writableDatabase.update(
            "Student",
            values,
            "StudentID = ?",
            arrayOf(id.toString())
        ) > 0
    }

    fun deleteStudent(id: Int): Boolean {
        return writableDatabase.delete(
            "Student",
            "StudentID = ?",
            arrayOf(id.toString())
        ) > 0
    }
}
