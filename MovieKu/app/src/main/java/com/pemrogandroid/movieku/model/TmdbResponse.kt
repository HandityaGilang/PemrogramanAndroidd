package com.pemrogandroid.movieku.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.squareup.picasso.Picasso

class TmdbResponse {

    @SerializedName("page")
    @Expose
    var page: Int? = null

    var title: String? = null

    var overview: String? = null

    var releasedate: String? = null

    var image: Picasso? = null

    @SerializedName("total_results")
    @Expose
    var totalResults: Int? = null

    @SerializedName("total_pages")
    @Expose
    var totalPages: Int? = null

    @SerializedName("results")
    @Expose
    var results: List<Movie>? = null
}