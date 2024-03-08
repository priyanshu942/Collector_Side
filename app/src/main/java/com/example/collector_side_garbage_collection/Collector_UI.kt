package com.example.collector_side_garbage_collection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.collector_side_garbage_collection.Fragments.HomeBlankFragment
import com.example.collector_side_garbage_collection.Fragments.SubmissionBlankFragment
import com.example.collector_side_garbage_collection.Fragments.profileBlankFragment
import com.example.collector_side_garbage_collection.databinding.ActivityCollectorUiBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class Collector_UI : AppCompatActivity() {
    private lateinit var binding: ActivityCollectorUiBinding
    private lateinit var requestsRef: DatabaseReference
    private lateinit var valueEventListener: ValueEventListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCollectorUiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeBlankFragment())
        setupBottomNavigation()

    }

    private fun setupBottomNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> replaceFragment(HomeBlankFragment())
                R.id.profile -> replaceFragment(profileBlankFragment())
                R.id.Submissions -> replaceFragment(SubmissionBlankFragment())
            }
            true
        }
    }





    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }
}
