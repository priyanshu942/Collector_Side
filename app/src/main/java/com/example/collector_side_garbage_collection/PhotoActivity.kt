package com.example.collector_side_garbage_collection

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class PhotoActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var userId: String
    private lateinit var imageUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)

        imageView = findViewById(R.id.imgv)

        // Retrieve user ID and image URL from intent extras
        userId = intent.getStringExtra("userId") ?: ""
        imageUrl = intent.getStringExtra("imageUrl") ?: ""
        Log.d("PhotoActivity", "User ID: $userId, Image URL: $imageUrl")

        // Check if userId and imageUrl are not empty
        if (userId.isNotEmpty() && imageUrl.isNotEmpty()) {
            Log.d("PhotoActivity", "Image URL: $imageUrl")
            // Load the image using Glide
            Glide.with(this)
                .load(imageUrl)
                .into(imageView)
        } else {
            Log.e("PhotoActivity", "User ID or Image URL is null or empty.")
        }
    }
}
