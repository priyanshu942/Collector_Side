package com.example.collector_side_garbage_collection
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class PhotoActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private val database = FirebaseDatabase.getInstance()
    private val usersRef = FirebaseStorage.getInstance().reference.child("users/null/images/08be677f-5b0d-4aff-8797-6ec0c43c4052.jpg")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)

        imageView = findViewById(R.id.imgv)
        val loc= File.createTempFile("tempImg","jpg")

        usersRef.getFile(loc).addOnSuccessListener {
            val bitmap=BitmapFactory.decodeFile(loc.absolutePath)
            imageView.setImageBitmap(bitmap)
        }.addOnFailureListener{
            Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show()
        }
//        usersRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//                for (userSnapshot in snapshot.children) {
//                    val user = userSnapshot.getValue(User::class.java)
//
//
//                    if (user != null) {
//                        val imageUrl = user.imageData?.imageUrl
//                        if (!imageUrl.isNullOrEmpty()) {
//                            Glide.with(this@PhotoActivity)
//                                .load("https://firebasestorage.googleapis.com/v0/b/garbagecollection-381dc.appspot.com/o/users%2Fnull%2Fimages%2F34914cd0-94a8-466c-9128-3efd55eb8e7a.jpg?alt=media&token=e229121c-4356-4216-b4d6-cbace5bf3f24")
//                                .into(imageView)
//                            break
//                        }
//                        else {
//
//                            Log.e(TAG, "Image URL is empty or null")
//                        }
//                    }
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.e(TAG, "Failed to read users.", error.toException())
//            }
//        })

    }

    companion object {
        private const val TAG = "PhotoActivity"
    }
}
