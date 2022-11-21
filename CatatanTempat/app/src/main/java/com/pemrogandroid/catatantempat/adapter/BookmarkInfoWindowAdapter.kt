package com.pemrogandroid.catatantempat.adapter

import android.app.Activity
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.pemrogandroid.catatantempat.ui.MapsActivity
import com.pemrogandroid.catatantempat.databinding.PlacesInfoBinding
import com.pemrogandroid.catatantempat.viewmodel.MapsViewModel

class BookmarkInfoWindowAdapter(val context: Activity) :
    GoogleMap.InfoWindowAdapter {

    private val binding = PlacesInfoBinding.inflate(context.layoutInflater)

    override fun getInfoContents(marker: Marker): View? {
        binding.title.text = marker.title ?: ""
        binding.phone.text = marker.snippet ?: ""

        val imageView = binding.photo
        when(marker.tag){
            is MapsActivity.PlaceInfo ->{
                imageView.setImageBitmap((marker.tag as MapsActivity.PlaceInfo).image)

            }
            is MapsViewModel.BookmarkMarkerView ->{
                val bookmarkView = marker.tag as MapsViewModel.BookmarkMarkerView
                imageView.setImageBitmap(bookmarkView.getImage(context))
            }
        }
        //val image = (marker.tag as MapsActivity.PlaceInfo).image
        //if (image !=null){
        //    imageView.setImageBitmap(image)
        //}

        return  binding.root

    }

    override fun getInfoWindow(marker: Marker): View? {
        return null
    }

}