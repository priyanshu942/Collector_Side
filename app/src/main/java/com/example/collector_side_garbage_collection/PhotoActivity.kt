package com.example.collector_side_garbage_collection
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.database.*

class PhotoActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private val database = FirebaseDatabase.getInstance()
    private val usersRef = database.getReference("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)

        imageView = findViewById(R.id.imgv)

        usersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue(User::class.java)


                    if (user != null) {
                        val imageUrl = user.imageData?.imageUrl
                        if (!imageUrl.isNullOrEmpty()) {
                            Glide.with(this@PhotoActivity)
                                .load(imageUrl)
                                .into(imageView)
                            break
                        } else {

                            Log.e(TAG, "Image URL is empty or null")
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Failed to read users.", error.toException())
            }
        })

    }

    companion object {
        private const val TAG = "PhotoActivity"
    }
}
