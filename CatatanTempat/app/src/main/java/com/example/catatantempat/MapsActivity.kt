package com.example.catatantempat

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout


import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.catatantempat.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlin.math.log

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var fusedLocationClient : FusedLocationProviderClient

    lateinit var lokasiku : Button
    lateinit var rumahku : Button
    lateinit var kampusku : Button
    var status = 0

    companion object{
        private const val REQUEST_LOCATION = 1
        private const val TAG = "MapsActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        lokasiku = findViewById(R.id.mylocation)
        rumahku = findViewById(R.id.rumah)
        kampusku = findViewById(R.id.kampus)

        lokasiku.setOnClickListener {object: View.OnClickListener {
            override fun onClick(v: View?) {
            status = 1
            }
        }
        }
        rumahku.setOnClickListener {object: View.OnClickListener {
            override fun onClick(v: View?) {
                status = 2
            }
        }}
        kampusku.setOnClickListener {object: View.OnClickListener {
            override fun onClick(v: View?) {
                status = 3
            }
        }}



        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION
            )
        } else if (status == 1) {

            fusedLocationClient.lastLocation.addOnCompleteListener {
                if (it.result != null) {
                    val sydney = LatLng(it.result.latitude, it.result.longitude)
                    mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
                    // untuk update kamera di zoom
                    //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,16.0f))
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16.0f))
                }
            }

        } else if (status == 2){
            val sydney = LatLng(-7.760534565929733, 110.4566699490647)
            mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
            // untuk update kamera di zoom
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,16.0f))
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16.0f))

        } else if (status==3){
            val sydney = LatLng(-7.785942995123726,110.37841861240469)
            mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
            // untuk update kamera di zoom
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,16.0f))
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16.0f))

        }




        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

    }


    fun rumah(){
        val sydney = LatLng(-7.760534565929733,110.4566699490647)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Rumah"))
        // untuk update kamera di zoom
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,16.0f))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16.0f))
    }

    fun Kampus(){
        val sydney = LatLng(-7.785942995123726,110.37841861240469)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Kampus"))
        // untuk update kamera di zoom
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,16.0f))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16.0f))
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION ){
            if (grantResults.size == 1 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Log.e(TAG,"LOCATION ACCEPT")
                }
                else{
                    Log.e(TAG,"LOCATION DENIED" )
                }
            }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera

    }
}