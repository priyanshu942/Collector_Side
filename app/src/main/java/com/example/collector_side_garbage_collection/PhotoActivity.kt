package com.example.collector_side_garbage_collection

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class PhotoActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var userId: String
    private lateinit var imageUrl: String
    private lateinit var storageRef: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)

        imageView=findViewById(R.id.imgv)


        userId=intent.getStringExtra("userId") ?: ""
        imageUrl=intent.getStringExtra("imageUrl") ?: ""


        if (userId.isNotEmpty() && imageUrl.isNotEmpty()) {

            val storage=FirebaseStorage.getInstance()
            storageRef=storage.reference.child("users/$userId/images/$imageUrl")

            loadUserDataAndDisplayImage()
        } else {
            Log.e("PhotoActivity", "User ID or Image URL is null or empty.")
        }
    }

    private fun loadUserDataAndDisplayImage() {
        val databaseReference=FirebaseDatabase.getInstance().getReference("users/$userId")

        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user=snapshot.getValue(User::class.java)

                if (user != null) {

                    val imageUrl= user.imageData?.imageUrl

                    if (!imageUrl.isNullOrEmpty()) {

                        Glide.with(this@PhotoActivity)
                            .load(imageUrl)
                            .into(imageView)
                    } else {
                        Log.e("PhotoActivity", "Image URL is null or empty.")
                    }
                } else {
                    Log.e("PhotoActivity", "User data is null.")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("PhotoActivity", "Error loading user data: ${error.message}")
            }
            })
        }
}