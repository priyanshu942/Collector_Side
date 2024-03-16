package com.example.collector_side_garbage_collection.Fragments
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.collector_side_garbage_collection.ImageData
import com.example.collector_side_garbage_collection.PhotoActivity
import com.example.collector_side_garbage_collection.R
import com.example.collector_side_garbage_collection.User
import com.example.collector_side_garbage_collection.UserAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.getValue

class HomeBlankFragment : Fragment() {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home_blank, container, false)

        userRecyclerView = view.findViewById(R.id.rv)
        userAdapter = UserAdapter(emptyList()) { userId, imageUrl ->


            Log.d("UserClick", "User clicked: $userId")

            val intent = Intent(requireContext(), PhotoActivity::class.java)
            intent.putExtra("userId", userId)

            intent.putExtra("imageUrl", imageUrl)
            startActivity(intent)
        }


        userRecyclerView.adapter = userAdapter
        userRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        databaseReference = FirebaseDatabase.getInstance().getReference("users")


        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val users = mutableListOf<User>()
                for (userSnapshot in snapshot.children) {
//                    val img=userSnapshot.child("imageData")
//                    val img1=img.getValue(ImageData::class.java)
                    val user = userSnapshot.getValue(User::class.java)
                   // val usr1= user?.imageData.toString()
                    user?.let {
                       // val img=it.imageData.logitude
                       val img=userSnapshot.child("imageData").getValue()
                        Log.d("FirebaseData", "User: ${it.userId}, Image URL: ${img}")
                        users.add(it) }
                }
                userAdapter.updateData(users)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })

        return  view
        }
}