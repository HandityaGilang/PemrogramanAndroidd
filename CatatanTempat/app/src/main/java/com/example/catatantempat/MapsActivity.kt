package com.example.catatantempat

import android.content.pm.PackageManager
import android.graphics.Bitmap
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
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.PointOfInterest
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPhotoRequest
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import kotlin.math.log

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var fusedLocationClient : FusedLocationProviderClient
    private lateinit var placesClient : PlacesClient

    companion object{
        private const val REQUEST_LOCATION = 1
        private const val TAG = "MapsActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



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
        } else{
            fusedLocationClient.lastLocation.addOnCompleteListener {
                if (it.result != null) {
                    val userLoc = LatLng(it.result.latitude, it.result.longitude)
                    mMap.addMarker(MarkerOptions().position(userLoc).title("My Loc"))
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLoc, 16.0f))
                }

            }


        }


        setupPlacesClient()


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

    }
    private fun setupPlacesClient(){
        Places.initialize(applicationContext, getString(R.string.google_maps_key))
        placesClient = Places.createClient(this)
    }
    private fun displayPoi(poi : PointOfInterest){
        val placeId = poi.placeId
        val placeFields = listOf(
            Place.Field.NAME,
            Place.Field.PHONE_NUMBER,
            Place.Field.PHOTO_METADATAS,
            Place.Field.ADDRESS,
            Place.Field.LAT_LNG,
        )

        val request = FetchPlaceRequest
            .builder(placeId, placeFields).build()

        placesClient.fetchPlace(request)
            .addOnCompleteListener { response ->
                val place = response.result.place
                displayPoiInfo(place)
            }
            .addOnFailureListener{
                if (it is ApiException){
                    Log.e(TAG, "displayPoi Exception: " + it.message + " error Code:" + it.statusCode)
                }
            }
    }

    private fun displayPoiInfo(place:Place){
        val photoMetadata = place.getPhotoMetadatas()?.get(0)

        if (photoMetadata == null){
            displayPoiPhoto(place, null)
            return
        }
        val photoRequest = FetchPhotoRequest.builder(photoMetadata)
            .setMaxHeight(resources.getDimensionPixelOffset(R.dimen.img_default_height))
            .setMaxWidth(resources.getDimensionPixelOffset(R.dimen.img_default_width))
            .build()
        placesClient.fetchPhoto(photoRequest)
            .addOnSuccessListener {
                val bitmap = it.bitmap
                displayPoiPhoto(place, bitmap)
            }
            .addOnFailureListener{
                if (it is ApiException){
                    Log.e(TAG, "displayPoi Exception: " + it.message + " error Code:" + it.statusCode)
                }
            }
    }
    private fun displayPoiPhoto(place: Place, photo :Bitmap?){
        val marker = mMap.addMarker(
            MarkerOptions().position(place.latLng as LatLng)
                .title(place.name)
                .snippet(place.phoneNumber)
        )
        marker?.tag = PlaceInfo(place, photo)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION ){
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.i(TAG,"onReqPermissionRes : location permission granted")

            }
        }else{
            Log.e(TAG, "onReqPermissionRes : location permission denied")

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

        setupMapListener()

        // Add a marker in Sydney and move the camera
//        val jogja = LatLng(-7.797068, 110.370529)
//        mMap.addMarker(MarkerOptions().position(jogja).title("Marker in jogja"))
////        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(jogja,16.0f))
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(jogja,16.0f))





    }
    private fun setupMapListener(){
        mMap.setOnPoiClickListener{
            mMap.setInfoWindowAdapter(BookmarkInfoWindowAdapter(this))
            mMap.setOnPoiClickListener{
            displayPoi(it)}
        }
        mMap.setOnInfoWindowClickListener {
            Log.i(TAG, "setOnInfoWindowClicklistener")
        }
    }

    class PlaceInfo(val place: Place?=null, val image:Bitmap?=null)
}