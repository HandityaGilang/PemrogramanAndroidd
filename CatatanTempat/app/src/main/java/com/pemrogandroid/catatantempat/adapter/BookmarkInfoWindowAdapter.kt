package com.pemrogandroid.catatantempat.adapter

import android.app.Activity
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.pemrogandroid.catatantempat.ui.MapsActivity
import com.pemrogandroid.catatantempat.databinding.PlacesInfoBinding

class BookmarkInfoWindowAdapter(context: Activity) :
    GoogleMap.InfoWindowAdapter {

    private val binding = PlacesInfoBinding.inflate(context.layoutInflater)

    override fun getInfoContents(marker: Marker): View? {
        binding.title.text = marker.title ?: ""
        binding.phone.text = marker.snippet ?: ""

        val imageView = binding.photo
        val image = (marker.tag as MapsActivity.PlaceInfo).image
        if (image !=null){
            imageView.setImageBitmap(image)
        }

        return  binding.root

    }

    override fun getInfoWindow(marker: Marker): View? {
        return null
    }

}