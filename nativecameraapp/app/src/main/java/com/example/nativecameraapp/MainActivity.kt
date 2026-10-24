
package com.example.nativecameraapp

import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var btnCapture: Button
    private lateinit var btnRetake: Button

    private var photoUri: Uri? = null

    private val cameraLauncher =
        registerForActivityResult(
            ActivityResultContracts.TakePicture()
        ) { success ->

            if (success && photoUri != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val values = ContentValues().apply {
                        put(MediaStore.Images.Media.IS_PENDING, 0)
                    }

                    contentResolver.update(
                        photoUri!!,
                        values,
                        null,
                        null
                    )
                }

                imageView.setImageURI(photoUri)
                btnCapture.visibility = View.GONE
                btnRetake.visibility = View.VISIBLE

                Toast.makeText(
                    this,
                    "Photo saved successfully",
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                photoUri?.let {
                    contentResolver.delete(it, null, null)
                }

                photoUri = null

                Toast.makeText(
                    this,
                    "Photo capture cancelled",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.imageView)
        btnCapture = findViewById(R.id.btnCapture)
        btnRetake = findViewById(R.id.btnRetake)

        btnCapture.setOnClickListener {
            capturePhoto()
        }

        btnRetake.setOnClickListener {
            capturePhoto()
        }
    }

    private fun capturePhoto() {
        try {
            val values = ContentValues().apply {
                put(
                    MediaStore.Images.Media.DISPLAY_NAME,
                    "Photo_${System.currentTimeMillis()}.jpg"
                )
                put(
                    MediaStore.Images.Media.MIME_TYPE,
                    "image/jpeg"
                )

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    put(
                        MediaStore.Images.Media.RELATIVE_PATH,
                        "Pictures/NativeCameraApp"
                    )
                    put(
                        MediaStore.Images.Media.IS_PENDING,
                        1
                    )
                }
            }

            photoUri = contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values
            )

            if (photoUri != null) {
                cameraLauncher.launch(photoUri!!)
            } else {
                Toast.makeText(
                    this,
                    "Unable to create image file",
                    Toast.LENGTH_LONG
                ).show()
            }

        } catch (e: Exception) {
            photoUri?.let {
                contentResolver.delete(it, null, null)
            }
            photoUri = null

            Toast.makeText(
                this,
                "Unable to open camera: ${e.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
