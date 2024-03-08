package com.example.collector_side_garbage_collection

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.collector_side_garbage_collection.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.google.firebase.Firebase
import android.Manifest.permission.ACCESS_FINE_LOCATION
import com.example.collector_side_garbage_collection.R.drawable.current
import com.example.collector_side_garbage_collection.R.drawable.current_location
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var databaseRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        databaseRef = FirebaseDatabase.getInstance().reference.child("users")
        databaseRef.addValueEventListener(logListner)
    }

    val logListner = object : ValueEventListener{

        override fun onDataChange(datasnapshot: DataSnapshot) {
            if(datasnapshot.exists()){

                mMap.clear()
                for (userSnapshot in datasnapshot.children) {
                    val imageDataSnapshot = userSnapshot.child("imageData").child(userSnapshot.key!!)
                    val latitude = imageDataSnapshot.child("latitude").getValue(Double::class.java)
                    val longitude = imageDataSnapshot.child("longitude").getValue(Double::class.java)
                    if (latitude != null && longitude != null) {
                        val userLocation = LatLng(latitude, longitude)
                        mMap.addMarker(MarkerOptions().position(userLocation).title(userSnapshot.child("name").getValue(String::class.java)))
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation))


                        Toast.makeText(applicationContext,"Sucessfully fetch data from database",Toast.LENGTH_LONG).show()

                    }
                }

            }
        }

        override fun onCancelled(error: DatabaseError) {
            Toast.makeText(applicationContext,"Could not fetch data from database",Toast.LENGTH_LONG).show()
        }


    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        } else {
            getCurrentLocation()
        }
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                val currentLocation = LatLng(location.latitude, location.longitude)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f))

                val blueMarkerIcon = BitmapDescriptorFactory.fromResource(current)
                mMap.addMarker(
                    MarkerOptions()
                        .position(currentLocation)
                        .title("Current Location")
                        .icon(blueMarkerIcon) // Set custom blue marker icon
                )
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation()
            } else {
                // Permission denied, handle accordingly
                Toast.makeText(applicationContext,"Provide permission",Toast.LENGTH_LONG).show()
            }
        }
    }



}